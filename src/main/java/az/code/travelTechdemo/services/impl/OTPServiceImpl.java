package az.code.travelTechdemo.services.impl;

import az.code.travelTechdemo.entities.OTP;
import az.code.travelTechdemo.repository.OTPRepository;
import az.code.travelTechdemo.services.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
    private final OTPRepository otpRepository;

    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 15;

    public OTP createOTP(String email) {
        String otp =String.valueOf(generateOTP());
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
        OTP newOTP = new OTP();
        newOTP.setEmail(email);
        newOTP.setOtp(otp);
        newOTP.setExpiryDate(expiryDate);
        newOTP.setUsed(false);
        otpRepository.save(newOTP);
        return newOTP;
    }
    private String generateOTP() {
        Random random = new Random();
        int otpValue = random.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%0" + OTP_LENGTH + "d", otpValue);
    }

    public boolean verifyOTP(String email, String otp) {
        Optional<OTP> otpRecord = otpRepository.findFirstByEmailAndOtpAndExpiryDateAfterAndUsedIsFalseOrderByExpiryDateDesc(email, otp, LocalDateTime.now());
        return otpRecord.map(otpRec -> {
            otpRec.setUsed(true);
            otpRepository.save(otpRec);
            return true;
        }).orElse(false);
    }
}
