package eu.gaminatorium.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NewGameDto {

    @Size(min=3, max = 30, message = "Game title must be 3-30 characters long.")
    private String title;
    @Size(min=10, max = 200, message = "Game description must be 10-200 characters long.")
    private String description;
    @Size(min=3, max = 200, message = "Add at least one tag, e.g. 'card-game' or 'action'. Separate multiple tags with space.")
    private String tags;
    @URL(message = "Url must begin with 'http://' or 'https://' ")
    private String gameUrl;
    @URL(message = "Url must begin with 'http://' or 'https://' ")
    private String sourceCodeUrl;
    @Min(value = 1, message = "Number must be a positive integer type")
    private int maxPlayers;
}
