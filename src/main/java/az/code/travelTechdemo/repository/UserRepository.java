package az.code.travelTechdemo.repository;


import az.code.travelTechdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    Optional<User> findAllByUsername(String userName);

    Optional<User> findAllByEmail(String email);
    User findByEmail(String email);

    boolean existsStudentByEmail(String email);


}
