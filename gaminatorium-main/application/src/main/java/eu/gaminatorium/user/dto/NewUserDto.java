package eu.gaminatorium.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewUserDto {

        @Size(min = 4, message = "Username must be at least 4 characters long")
        private String userName;
        @Email
        private String email;
        @Size(min = 8, message = "Password must be at least 8 characters long")
        private String password;

}
