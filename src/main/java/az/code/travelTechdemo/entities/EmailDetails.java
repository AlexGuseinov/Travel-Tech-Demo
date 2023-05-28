package az.code.travelTechdemo.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
