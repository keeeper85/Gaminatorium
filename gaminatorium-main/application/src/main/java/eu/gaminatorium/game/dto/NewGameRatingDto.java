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
    @Min(value = 1, message = "The score must be at least 1")
    @Max(value = 10, message = "The score must be at most 10")
    int score;
    @Min(value = 0, message = "The game gameid is a positive integer number.")
    long gameid;
}
