package eu.gaminatorium.game.dto;

import eu.gaminatorium.game.Game;
import eu.gaminatorium.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record GameDto(
        Long id,
        Game.ModerationStatus moderationStatus,
        String title,
        String description,
        String tags,
        String gameUrl,
        String sourceCodeUrl,
        int maxPlayers,
        long timesPlayedTotal,
        User author,
        LocalDate releaseDate,
        LocalDateTime lastTimePlayed,
        Set<Game.Rating> ratings,
        Set<Game.Active> activeGames) {
}
