package eu.gaminatorium.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private String userName;
    private String email;
    private String password;
}
