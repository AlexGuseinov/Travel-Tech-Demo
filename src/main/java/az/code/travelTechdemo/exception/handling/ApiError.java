package az.code.travelTechdemo.exception.handling;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiError {

  private String path;
  private String message;
  private Integer statusCode;
  private LocalDateTime localDateTime;
}