package eu.gaminatorium.game.dto;

import eu.gaminatorium.game.Game;
import lombok.Builder;

import java.time.LocalDate;

@Builder public record RatingDto(String comment, int score, LocalDate postDate, Game game) {
}
