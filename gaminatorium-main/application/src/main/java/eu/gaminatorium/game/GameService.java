package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class GameService {

    private final GameRepository gameRepository;


    int countAllAvailableGames() {
    return gameRepository.countAllByModerationStatus(Game.ModerationStatus.ACCEPTED);
    }

    List<GameDto> getAllAvailableGamesPaged(Pageable pageable){
        return gameRepository.findAll(pageable).getContent().stream()
                .map(GameService::toDto)
                .toList();
    }

    Optional<GameDto> getGameById(long id) {
        if (gameRepository.existsById(id)){
            return Optional.of(GameService.toDto(gameRepository.findById(id)));
        }
        return Optional.empty();
    }

    Optional<GameDto> addNewGame(GameDto gameDto){
        gameRepository.save(Factory.from(gameDto));
        return Optional.of(gameDto);
    }

    Optional<GameDto> updateGame(long id){
        if (gameRepository.existsById(id)){
            //todo
        }
        return Optional.empty();
    }

    Optional<Void> deleteGame(long id){
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
        }
        return Optional.empty();
    }

    public List<GameDto> getMatchingGamesPaged(Pageable pageable) {
        return null;
    }

    public boolean toggleGameStatus(long gameId) {
        if (gameRepository.existsById(gameId)) {
            var game = gameRepository.findById(gameId);
            game.toggleModerationStatus();
            gameRepository.save(game);
            return true;
        }
        return false;
    }

    private static GameDto toDto(Game game) {
        GameDto gameDto = GameDto.builder()
                .id(game.getId())
                .moderationStatus(game.getModerationStatus())
                .title(game.getTitle())
                .description(game.getDescription())
                .tags(game.getGameTags())
                .gamelink(game.getGameServiceLink())
                .sourceCodelink(game.getSourceCodeLink())
                .maxPlayers(game.getMaxPlayers())
                .timesPlayedTotal(game.getTimesPlayedTotal())
                .releaseDate(game.getReleaseDate())
                .ratings(game.getRatings())
                .activeGames(game.getActiveGames())
                .build();
        return gameDto;
    }
}
