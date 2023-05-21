package az.code.travelTechdemo.services;

import az.code.travelTechdemo.models.request.AuthenticationRequest;
import az.code.travelTechdemo.models.request.RegisterRequest;
import az.code.travelTechdemo.models.response.AuthenticationResponse;

public interface AuthenticationService {
   AuthenticationResponse login(AuthenticationRequest request);
   AuthenticationResponse register(RegisterRequest request);
}