package az.code.travelTechdemo.repository;

import az.code.travelTechdemo.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findFirstByEmailAndOtpAndExpiryDateAfterAndUsedIsFalseOrderByExpiryDateDesc(String email, String otp, LocalDateTime dateTime);
}
