package az.code.travelTechdemo.repository;

import az.code.travelTechdemo.models.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join User u on t.user.id=u.id where u.id=?1 and t.expired=false or t.revoked=false ")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
