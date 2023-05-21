package az.code.travelTechdemo.repository;

import az.code.travelTechdemo.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsStudentByEmail(String email);

  Optional<User> findUserByEmail(String email);
}
