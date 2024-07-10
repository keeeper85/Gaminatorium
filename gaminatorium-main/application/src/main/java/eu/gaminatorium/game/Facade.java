package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Facade {

    private final GameService gameService;
    private final ActiveGameService activeGameService;
    private final GameRatingService gameRatingService;

    int countAllAvailableGames() {
        return gameService.countAllAvailableGames();
    }

    boolean toggleGameStatus(long gameId) {
        return gameService.toggleGameStatus(gameId);
    }

    List<GameDto> getAllAvailableGamesPaged(Game.ModerationStatus moderationStatus, Pageable pageable){
        return gameService.getGamesPaged(moderationStatus, pageable);
    }

    Optional<GameDto> getGameById(long id) {
        return gameService.getGameById(id);
    }

    Optional<NewGameDto> addNewGame(NewGameDto gameDto){
        return gameService.addNewGame(gameDto);
    }

    Optional<GameDto> updateGame(long id, NewGameDto newGameDto){
        return gameService.updateGame(id, newGameDto);
    }

    void deleteGame(long id){
        gameService.deleteGame(id);
    }

    List<ActiveGameDto> getMatchingActiveGamesPaged(String title, Pageable pageable) {
        return activeGameService.getMatchingActiveGamesPaged(title, pageable);
    }

    List<GameDto> findAvailableMatchingGamesPaged(String title, Pageable pageable) {
        return gameService.findMatchingAvailableGamesPaged(title, pageable);
    }

    String[] getGameTags(long gameid) {
        return gameService.getGameTags(gameid);
    }

    boolean isGameTitleUsed(String title){
        return gameService.isGameTitleUsed(title);
    }

    public Optional<String> getCurrentGameScore(long gameid) {
        return gameRatingService.getCurrentGameScore(gameid);
    }

    public Optional<GameRatingDto> getRandomRating(long gameid) {
        return gameRatingService.getRandomRating(gameid);
    }

    public List<GameRatingDto> getAllRatingsPaged(long gameid, Pageable pageable) {
        return gameRatingService.getAllRatingsPaged(gameid, pageable);
    }

    public Optional<NewGameRatingDto> addRating(NewGameRatingDto rating) {
        return gameRatingService.addRating(rating);
    }

    public Optional<ActiveGameDto> startNewGame(long gameid) {
        return activeGameService.startNewGame(gameid);
    }

    public Optional<List<ActiveGameDto>> getAllActiveGamesForThisGame(long gameid, Pageable pageable) {
        return activeGameService.getAllActiveGamesForThisGame(gameid, pageable);
    }

    public List<ActiveGameDto> getAllActiveGamesForAllGames(Pageable pageable) {
        return activeGameService.getAllActiveGamesForAllGames(pageable);
    }

    public Optional<ActiveGameDto> joinGame(long activegameid) {
        return activeGameService.joinGame(activegameid);
    }

    public void deleteRating(long ratingId) {
        gameRatingService.deleteRating(ratingId);
    }

    public List<GameDto> getRecentlyPlayedGames(Pageable pageable) {
        return gameService.getRecentlyPlayedGames(pageable);
    }
}
