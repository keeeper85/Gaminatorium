package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.GameRatingDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class ActiveGameService {

    GameRepository gameRepository;

    public List<ActiveGameDto> getMatchingActiveGamesPaged(String title, Pageable pageable) {
        return null;
    }

    public Optional<ActiveGameDto> startNewGame(long gameid) {
        if (gameRepository.existsById(gameid)) {
            var game = gameRepository.findById(gameid);
            var activeGame = game.startNewGame();
            gameRepository.save(game);
            return Optional.of(toDto(activeGame));
        }
        return Optional.empty();
    }

    public List<ActiveGameDto> getAllActiveGamesForThisGame(long gameid, Pageable pageable) {
        if (gameRepository.existsById(gameid)) {
            return gameRepository.findAllActiveGamesByGameId(gameid, pageable).map(ActiveGameService::toDto).toList();
        }
        return List.of();
    }

    private static ActiveGameDto toDto(Game.Active activeGame) {
        ActiveGameDto activeGameDto = new ActiveGameDto(activeGame.getGame());
        activeGameDto.setCurrentPlayers(activeGame.getCurrentPlayers());
        activeGameDto.setFull(activeGame.isFull());
        activeGameDto.setId(activeGame.getId());
        activeGameDto.setMaxPlayers(activeGameDto.getMaxPlayers());
        activeGameDto.setStartedAt(activeGame.getStartedAt());

        return activeGameDto;
    }
}
