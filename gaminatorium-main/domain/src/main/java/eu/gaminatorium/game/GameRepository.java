package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameRepository {

    Game save(Game game);
    Page<Game> findAll(Pageable pageable);
    Page<Game.Rating> findAllRatingsByGameId(Long gameId, Pageable pageable);
    Page<Game.Active> findAllActiveGamesByGameId(Long gameId, Pageable pageable);
    Game findById(long id);
    Game.Active findActiveByTitle(String title);
    Game.Active save(Game.Active active);
    Game.Rating save(Game.Rating rating);
    Page<Game> findAllByTitle(String title, Pageable pageable);
    List<Game> findGamesOrderedByLastTimePlayedDesc(Pageable pageable);
    Page<Game> findAllByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Game> findAllByModerationStatusIs(Game.ModerationStatus moderationStatus, Pageable pageable);
    Page<Game.Active> findAllActiveGamesBy(Pageable pageable);
    Page<Game.Active> findActiveByTitle(String title, Pageable pageable);
    Game.Active findActiveGameById(Long id);
    Game.Rating findGameRatingById(Long id);
    void deleteById(long id);
    int countAllByModerationStatus(Game.ModerationStatus moderationStatus);
    boolean existsById(long id);
    boolean existsByTitle(String title);
    boolean existsActiveGameById(long id);
    boolean existsGameRatingById(long id);
}
