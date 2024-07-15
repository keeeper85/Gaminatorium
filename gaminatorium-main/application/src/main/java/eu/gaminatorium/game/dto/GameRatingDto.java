package eu.gaminatorium.game.dto;

import eu.gaminatorium.user.dto.UserDto;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GameRatingDto(
        Long ratingid,
        String comment,
        int score,
        LocalDate postDate,
        long gameid,
        UserDto author) {
}
