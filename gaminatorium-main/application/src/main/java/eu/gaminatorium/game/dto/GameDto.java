package eu.gaminatorium.game.dto;

import eu.gaminatorium.game.Game;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record GameDto(
        Long id,
        Game.ModerationStatus moderationStatus,
        String title,
        String description,
        String tags,
        String gamelink,
        String sourceCodelink,
        int maxPlayers,
        long timesPlayedTotal,
        LocalDate releaseDate,
        Set<Game.Rating> ratings,
        Set<Game.Active> activeGames) {
}
