package az.code.travelTechdemo.controllers;

import az.code.travelTechdemo.services.impl.AuthenticationServiceImpl;
import az.code.travelTechdemo.services.impl.UserServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel/auth")
public class ForgetPasswordController {

    private final UserServiceimpl userService;
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam String email) throws IOException {
                return ResponseEntity.ok(authenticationService.forgetPassword(email));
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> forgetPassword() {
        return ResponseEntity.ok("alalalaa codu yaz davay!!");
    }

}