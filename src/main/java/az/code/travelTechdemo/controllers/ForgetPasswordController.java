package az.code.travelTechdemo.controllers;

import az.code.travelTechdemo.models.request.PasswordResetRequest;
import az.code.travelTechdemo.models.request.RegisterRequest;
import az.code.travelTechdemo.models.response.AuthenticationResponse;
import az.code.travelTechdemo.services.impl.AuthenticationServiceImpl;
import az.code.travelTechdemo.services.impl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel/auth/forgetpassword")
public class ForgetPasswordController {

    private final UserServiceimpl userService;
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/otp-sending")
    public ResponseEntity<String> forgetPassword(@RequestParam String email) throws IOException {
        return ResponseEntity.ok(authenticationService.forgetPassword(email));
    }

    @PostMapping("/otp-checking")
    public ResponseEntity<Boolean> otpChecker(@RequestParam String otp,
                                              @RequestParam String email) {
        return ResponseEntity.ok(authenticationService.otpCheck(email, otp));
    }

    @PostMapping("/password-resetting")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody PasswordResetRequest request) {
        return   ResponseEntity.ok(authenticationService.passwordReset(request));
    }

}