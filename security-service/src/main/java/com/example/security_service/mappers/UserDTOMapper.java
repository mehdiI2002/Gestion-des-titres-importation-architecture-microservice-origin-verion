package com.example.security_service.mappers;

import com.example.security_service.dto.UserDTO;
import com.example.security_service.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstName(),
               user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
