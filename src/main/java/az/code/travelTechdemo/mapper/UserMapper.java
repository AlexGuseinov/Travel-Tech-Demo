package az.code.travelTechdemo.mapper;


import az.code.travelTechdemo.entities.User;
import az.code.travelTechdemo.models.request.PasswordResetRequest;
import az.code.travelTechdemo.models.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User mapToUser(RegisterRequest userRegisterRequest);
  User mapToPasswordResetUser(PasswordResetRequest passwordResetRequest);

}
