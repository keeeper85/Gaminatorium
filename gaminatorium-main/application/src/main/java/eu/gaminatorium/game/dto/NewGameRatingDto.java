package eu.gaminatorium.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewGameRatingDto {

    String comment;
    int score;
    long gameid;
}
