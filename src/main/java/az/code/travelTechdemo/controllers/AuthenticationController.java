package az.code.travelTechdemo.controllers;

import az.code.travelTechdemo.models.request.AuthenticationRequest;
import az.code.travelTechdemo.models.request.RegisterRequest;
import az.code.travelTechdemo.models.response.AuthenticationResponse;
import az.code.travelTechdemo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

}