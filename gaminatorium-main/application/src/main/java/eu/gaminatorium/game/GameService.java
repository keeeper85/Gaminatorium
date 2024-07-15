package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.GameDto;
import eu.gaminatorium.game.dto.NewGameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    Optional<GameDto> getGameById(long gameid) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()){
            return Optional.of(GameService.toDto(gameOptional.get()));
        }
        return Optional.empty();
    }

    Optional<NewGameDto> addNewGame(NewGameDto newGameDto){
        if (isGameTitleUsed(newGameDto.getTitle())){
            return Optional.of(new NewGameDto("Error! Chosen title already exists!",
                    newGameDto.getDescription(),
                    newGameDto.getTags(),
                    newGameDto.getGameUrl(),
                    newGameDto.getSourceCodeUrl(),
                    newGameDto.getMaxPlayers()));
        }
        gameRepository.save(Factory.from(newGameDto));
        return Optional.of(newGameDto);
    }

    Optional<GameDto> updateGame(long gameid, NewGameDto newGameDto){
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()){
            Game game = gameOptional.get();
            if (newGameDto.getTitle() != null) game.setTitle(newGameDto.getTitle());
            if (newGameDto.getDescription() != null) game.setDescription(newGameDto.getDescription());
            if (newGameDto.getTags() != null) game.setGameTags(newGameDto.getTags());
            if (newGameDto.getGameUrl() != null) game.setGameServiceUrl(newGameDto.getGameUrl());
            if (newGameDto.getSourceCodeUrl()!= null) game.setSourceCodeUrl(newGameDto.getSourceCodeUrl());
            if (newGameDto.getMaxPlayers() > 0) game.setMaxPlayers(newGameDto.getMaxPlayers());
            gameRepository.save(game);
            return Optional.of(GameService.toDto(game));
        }
        return Optional.empty();
    }

    void deleteGame(long gameid){
        gameRepository.deleteById(gameid);
    }

    boolean toggleGameStatus(long gameid) {
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()){
            Game game = gameOptional.get();
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
        Optional<Game> gameOptional = gameRepository.findById(gameid);
        if (gameOptional.isPresent()){
            Game game = gameOptional.get();
            String[] tags = game.getGameTags().split(" ");
            return tags;
        }
        return null;
    }

    boolean isGameTitleUsed(String title){
        return gameRepository.existsByTitle(title);
    }

    public List<GameDto> getRecentlyPlayedGames(Pageable pageable) {
        return gameRepository.findGamesOrderedByLastTimePlayedDesc(pageable).stream().map(GameService::toDto).toList();
    }

    private static GameDto toDto(Game game) {
        GameDto gameDto = GameDto.builder()
                .gameid(game.getId())
                .moderationStatus(game.getModerationStatus())
                .title(game.getTitle())
                .description(game.getDescription())
                .tags(game.getGameTags())
                .gameUrl(game.getGameServiceUrl())
                .sourceCodeUrl(game.getSourceCodeUrl())
                .maxPlayers(game.getMaxPlayers())
                .timesPlayedTotal(game.getTimesPlayedTotal())
                .releaseDate(game.getReleaseDate())
                .lastTimePlayed(game.getLastTimePlayed())
                .ratings(game.getRatings())
                .activeGames(game.getActiveGames())
                .build();
        return gameDto;
    }
}
