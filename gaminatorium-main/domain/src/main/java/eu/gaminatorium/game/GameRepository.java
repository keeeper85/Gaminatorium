package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    boolean existsByTitle(String title);
    Game save(Game game);
    Optional<Game> findById(long id);
    void deleteById(long id);
    int countAllByModerationStatus(Game.ModerationStatus moderationStatus);
    void reassignGamesToFirstUser(Long userId);

    Page<Game> findAllByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Game> findAllByModerationStatusIs(Game.ModerationStatus moderationStatus, Pageable pageable);
    List<Game> findGamesOrderedByLastTimePlayedDesc(Pageable pageable);

    Optional<Game.Active> findActiveGameById(Long id);
    Page<Game.Active> findAllActiveGamesByGameId(Long gameId, Pageable pageable);
    Page<Game.Active> findAllActiveGamesBy(Pageable pageable);
    Page<Game.Active> findAllActiveGamesByTitleContaining(String title, Pageable pageable);

    Page<Game.Rating> findAllRatingsByGameId(Long gameId, Pageable pageable);
    Optional<Game.Rating> findGameRatingById(Long id);

}
