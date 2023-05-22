package az.code.travelTechdemo.services;

import az.code.travelTechdemo.auth.AuthenticationRequest;
import az.code.travelTechdemo.auth.AuthenticationResponse;
import az.code.travelTechdemo.auth.RegisterRequest;
import az.code.travelTechdemo.models.entities.Token;
import az.code.travelTechdemo.models.entities.User;
import az.code.travelTechdemo.models.enums.Role;
import az.code.travelTechdemo.models.enums.TokenType;
import az.code.travelTechdemo.repository.TokenRepository;
import az.code.travelTechdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

//    private final UserRepository userRepository;
    private final UserServiceimpl userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        User user = User.builder()
//                .firstName(registerRequest.getFirstName())
//                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
//                .phoneNumber(registerRequest.getPhoneNumber())
//                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.User)
                .build();
        User savedUser = userService.saveUser(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser,jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userService.getUserByEmail(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
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
}