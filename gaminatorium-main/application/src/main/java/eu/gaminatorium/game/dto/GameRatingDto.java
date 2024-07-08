package eu.gaminatorium.game.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GameRatingDto(long ratingId, String comment, int score, LocalDate postDate, long gameid) {
}
