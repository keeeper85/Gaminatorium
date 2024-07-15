package eu.gaminatorium.user.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import eu.gaminatorium.game.Game;
import eu.gaminatorium.game.dto.GameDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class UserDto {
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String userName;
    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String lastGamePlayed;
    private Set<String> favoriteGames;
}
