package az.code.travelTechdemo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

//    private String firstName;

//    private String lastName;

    private String email;

//    private String phoneNumber;

//    private String username;

    private String password;
}
