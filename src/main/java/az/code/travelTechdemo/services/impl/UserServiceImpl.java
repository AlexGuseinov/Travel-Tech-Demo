package az.code.travelTechdemo.services.impl;

import az.code.travelTechdemo.entities.User;
import az.code.travelTechdemo.repository.UserRepository;
import az.code.travelTechdemo.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User findByUserName(String username){
        return userRepository.findUserByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));    }
}
