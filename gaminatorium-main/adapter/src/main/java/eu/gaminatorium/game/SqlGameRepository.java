package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface SqlGameRepository extends GameRepository, JpaRepository<Game, Integer> {

    boolean existsByTitle(String title);
    Game save(Game game);
    Optional<Game> findById(long id);
    void deleteById(long id);
    int countAllByModerationStatus(Game.ModerationStatus moderationStatus);

    Page<Game> findAllByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Game> findAllByModerationStatusIs(Game.ModerationStatus moderationStatus, Pageable pageable);

    @Query("SELECT g FROM Game g ORDER BY g.lastTimePlayed DESC")
    List<Game> findGamesOrderedByLastTimePlayedDesc(Pageable pageable);

    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE a.id = :id")
    Optional<Game.Active> findActiveGameById(@Param("id") Long id);

    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE g.id = :gameId")
    Page<Game.Active> findAllActiveGamesByGameId(@Param("gameId") Long gameId, Pageable pageable);

    @Query("SELECT a FROM Game g JOIN g.activeGames a")
    Page<Game.Active> findAllActiveGamesBy(Pageable pageable);

    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE g.title LIKE %:title%")
    Page<Game.Active> findAllActiveGamesByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query("SELECT r FROM Game g JOIN g.ratings r WHERE g.id = :gameId")
    Page<Game.Rating> findAllRatingsByGameId(@Param("gameId") Long gameId, Pageable pageable);

    @Query("SELECT r FROM Game g JOIN g.ratings r WHERE r.id = :id")
    Optional<Game.Rating> findGameRatingById(@Param("id") Long id);
}
