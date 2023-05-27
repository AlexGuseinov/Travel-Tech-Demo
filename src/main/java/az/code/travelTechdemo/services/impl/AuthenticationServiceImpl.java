package az.code.travelTechdemo.services.impl;

import az.code.travelTechdemo.entities.Token;
import az.code.travelTechdemo.entities.User;
import az.code.travelTechdemo.entities.enums.Role;
import az.code.travelTechdemo.entities.enums.TokenType;
import az.code.travelTechdemo.exception.DuplicateResourceException;
import az.code.travelTechdemo.mapper.UserMapper;
import az.code.travelTechdemo.models.request.AuthenticationRequest;
import az.code.travelTechdemo.models.request.RegisterRequest;
import az.code.travelTechdemo.models.response.AuthenticationResponse;
import az.code.travelTechdemo.repository.TokenRepository;
import az.code.travelTechdemo.repository.UserRepository;
import az.code.travelTechdemo.security.jwt.JWTUtil;
import az.code.travelTechdemo.services.AuthenticationService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    UserMapper userMapper = UserMapper.INSTANCE;

    private final JWTUtil jwtUtil;
    private final CacheManager cacheManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final MailSenderImpl mailSender;

    @Override
    public AuthenticationResponse register(RegisterRequest request){
        log.info("register().start email: {}", request.getEmail());

        String email = request.getEmail();
        if (userRepository.existsStudentByEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }

        var user = userMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        var roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        var savedUser = userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(user.getUsername(), roles);
        saveUserToken(savedUser,jwtToken);

        Objects.requireNonNull(cacheManager.getCache("response")).clear();

        log.info("register().end user-id: {}", user.getId());

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request){
        log.info("login().start username: {}", request.getEmail());

        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = (User) authentication.getPrincipal();

        var roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        String jwtToken = jwtUtil.generateToken(user.getEmail(), roles);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        log.info("login().end user-id: {}", user.getId());

        return  AuthenticationResponse.builder().token(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    //todo test

    public String generatePasswordResetLink(String email) {
        User user = userRepository.findByEmail(email);
        String token = jwtUtil.generateToken(user.getEmail());
        return "http://localhost:8080/v1/travel/auth/reset-password?token=" + token;
    }
    public String forgetPassword(String email) throws IOException {
        if (userRepository.existsStudentByEmail(email)) {
            String resetLink = generatePasswordResetLink(email);
            mailSender.sendPasswordResetEmail(userRepository.findByEmail(email), resetLink);
            return "size mail gonderdik";
        } else {
            throw new UsernameNotFoundException("this user doesn't exist: " + email);
        }
    }

}