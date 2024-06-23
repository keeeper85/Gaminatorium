package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.NewGameDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class GameService {

    private final GameRepository gameRepository;


    int countAllAvailableGames() {
    return gameRepository.countAllByModerationStatus(Game.ModerationStatus.ACCEPTED);
    }

    List<GameDto> getGamesPaged(Game.ModerationStatus moderationStatus, Pageable pageable){
        return gameRepository.findAllByModerationStatusIs(moderationStatus, pageable).getContent().stream()
                .map(GameService::toDto)
                .toList();
    }

    Optional<GameDto> getGameById(long id) {
        if (gameRepository.existsById(id)){
            return Optional.of(GameService.toDto(gameRepository.findById(id)));
        }
        return Optional.empty();
    }

    Optional<NewGameDto> addNewGame(NewGameDto newGameDto){
        if (isGameTitleUsed(newGameDto.getTitle())){
            return Optional.of(new NewGameDto("Error! Chosen title already exists!",
                    newGameDto.getDescription(),
                    newGameDto.getTags(),
                    newGameDto.getGamelink(),
                    newGameDto.getSourceCodelink(),
                    newGameDto.getMaxPlayers()));
        }
        gameRepository.save(Factory.from(newGameDto));
        return Optional.of(newGameDto);
    }

    Optional<GameDto> updateGame(long gameid, NewGameDto newGameDto){
        if (gameRepository.existsById(gameid)){
            var game = gameRepository.findById(gameid);
            if (newGameDto.getTitle() != null) game.setTitle(newGameDto.getTitle());
            if (newGameDto.getDescription() != null) game.setDescription(newGameDto.getDescription());
            if (newGameDto.getTags() != null) game.setGameTags(newGameDto.getTags());
            if (newGameDto.getGamelink() != null) game.setGameServiceLink(newGameDto.getGamelink());
            if (newGameDto.getSourceCodelink()!= null) game.setSourceCodeLink(newGameDto.getSourceCodelink());
            if (newGameDto.getMaxPlayers() > 0) game.setMaxPlayers(newGameDto.getMaxPlayers());
            gameRepository.save(game);
            return Optional.of(GameService.toDto(game));
        }
        return Optional.empty();
    }

    Optional<Void> deleteGame(long id){
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
        }
        return Optional.empty();
    }

    boolean toggleGameStatus(long gameId) {
        if (gameRepository.existsById(gameId)) {
            var game = gameRepository.findById(gameId);
            game.toggleModerationStatus();
            gameRepository.save(game);
            return true;
        }
        return false;
    }

    List<GameDto> findMatchingAvailableGamesPaged(String title, Pageable pageable) {
        return gameRepository.findAllByTitleIsContainingIgnoreCase(title, pageable).stream()
                .filter(game -> game.getModerationStatus() == Game.ModerationStatus.ACCEPTED)
                .map(GameService::toDto).toList();
    }

    String[] getGameTags(long gameid) {
        if (gameRepository.existsById(gameid)) {
            var game = gameRepository.findById(gameid);
            String[] tags = game.getGameTags().split(" ");
            return tags;
        }
        return null;
    }

    boolean isGameTitleUsed(String title){
        return gameRepository.existsByTitle(title);
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
