package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ActiveGameDto;
import eu.gaminatorium.user.TestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ActiveGameService {

    private final GameRepository gameRepository;

    public List<ActiveGameDto> getMatchingActiveGamesPaged(String title, Pageable pageable) {
        return gameRepository.findAllActiveGamesByTitleContaining(title, pageable).map(ActiveGameService::toDto).toList();
    }

    public Optional<ActiveGameDto> startNewGame(long gameid) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            var activeGame = game.startNewGame(TestUser.TEST_USER); //todo
            gameRepository.save(game);
            return Optional.of(toDto(activeGame));
        }
        return Optional.empty();
    }

    public Optional<List<ActiveGameDto>> getAllActiveGamesForThisGame(long gameid, Pageable pageable) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()) {
            return Optional.of(gameRepository.findAllActiveGamesByGameId(gameid, pageable).map(ActiveGameService::toDto).toList());
        }
        return Optional.empty();
    }

    public List<ActiveGameDto> getAllActiveGamesForAllGames(Pageable pageable) {
        return gameRepository.findAllActiveGamesBy(pageable).map(ActiveGameService::toDto).toList();
    }

    public Optional<ActiveGameDto> joinGame(long activegameid) {
        Optional<Game.Active> activeOptional = gameRepository.findActiveGameById(activegameid);
        if (activeOptional.isPresent()){
            Game.Active activeGame = activeOptional.get();
            Game game = activeGame.getGame();
            game.joinExistingActiveGame(activeGame, TestUser.TEST_USER);
            gameRepository.save(game);
            return Optional.of(toDto(activeGame));
        }
        return Optional.empty();
    }

    private static ActiveGameDto toDto(Game.Active activeGame) {
        ActiveGameDto activeGameDto = new ActiveGameDto(activeGame.getGame());
        activeGameDto.setActivegameid(activeGame.getId());
        activeGameDto.setCurrentPlayers(activeGame.getCurrentPlayers());
        activeGameDto.setMaxPlayers(activeGameDto.getMaxPlayers());
        activeGameDto.setStartedAt(activeGame.getStartedAt());

        return activeGameDto;
    }
}
