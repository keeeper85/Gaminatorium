package eu.gaminatorium.game.dto;

import eu.gaminatorium.game.Game;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record GameDto(Long id, String title, String description, String link, int maxPlayers, LocalDate releaseDate, Set<Game.Rating> ratings, Set<Game.Active> activeGames) {
}
