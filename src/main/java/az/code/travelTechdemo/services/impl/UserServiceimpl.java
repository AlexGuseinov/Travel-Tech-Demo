package az.code.travelTechdemo.services.impl;

import az.code.travelTechdemo.entities.User;
import az.code.travelTechdemo.repository.UserRepository;
import az.code.travelTechdemo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findAllByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Couldn't found the user: " + email)
        );
    }

    public User saveUser(User user) {
        if (userRepository.findAllByEmail(user.getEmail()).isEmpty())
            userRepository.save(user);
        return user;
    }



}
