package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class Facade {

    private final GameService gameService;
    private final ActiveGameService activeGameService;
    private final GameRatingService gameRatingService;

    int countAllGames() {
        return gameService.countAllGames();
    }

    List<GameDto> getAllGamesPaged(Pageable pageable){
        return gameService.getAllGamesPaged(pageable);
    }

    Optional<GameDto> getGameById(long id) {
        return gameService.getGameById(id);
    }

    Optional<GameDto> addNewGame(GameDto gameDto){
        return gameService.addNewGame(gameDto);
    }

    Optional<GameDto> updateGame(long id){
        return gameService.updateGame(id);
    }

    Optional<Void> deleteGame(long id){
        return gameService.deleteGame(id);
    }
}
