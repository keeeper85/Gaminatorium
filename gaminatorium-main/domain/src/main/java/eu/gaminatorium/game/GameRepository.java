package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameRepository {

    boolean existsById(long id);
    boolean existsByTitle(String title);
    Game save(Game game);
    Game findById(long id);
    void deleteById(long id);
    int countAllByModerationStatus(Game.ModerationStatus moderationStatus);

    Page<Game> findAllByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Game> findAllByModerationStatusIs(Game.ModerationStatus moderationStatus, Pageable pageable);
    List<Game> findGamesOrderedByLastTimePlayedDesc(Pageable pageable);

    boolean existsActiveGameById(long id);
    Game.Active findActiveGameById(Long id);
    Page<Game.Active> findAllActiveGamesByGameId(Long gameId, Pageable pageable);
    Page<Game.Active> findAllActiveGamesBy(Pageable pageable);

    boolean existsGameRatingById(long id);
    Page<Game.Rating> findAllRatingsByGameId(Long gameId, Pageable pageable);
    Game.Rating findGameRatingById(Long id);

}
