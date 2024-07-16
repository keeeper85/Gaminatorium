package eu.gaminatorium.game.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewGameRatingDto {

    @NotBlank(message = "Comment can not be blank")
    String comment;
    @Min(value = 1, message = "The score must be between 1 and 10")
    @Max(value = 10, message = "The score must be between 1 and 10")
    int score;
    @Min(value = 1, message = "The game gameid is a positive integer number.")
    long gameid;
}
