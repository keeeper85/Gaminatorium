package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

interface SqlGameRepository extends GameRepository, JpaRepository<Game, Integer> {

    Game save(Game game);
    Page<Game> findAll(Pageable pageable);
    @Query("SELECT r FROM Game g JOIN g.ratings r WHERE g.id = :gameId")
    Page<Game.Rating> findAllRatingsByGameId(@Param("gameId") Long gameId, Pageable pageable);
    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE a.game.id = :gameId")
    Page<Game.Active> findAllActiveGamesByGameId(@Param("gameId") Long gameId, Pageable pageable);
    Game findById(long id);
    Page<Game> findAllByModerationStatusIs(Game.ModerationStatus moderationStatus, Pageable pageable);
    Page<Game> findAllByTitle(String title, Pageable pageable);
    Page<Game> findAllByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Game.Active> findActiveByTitle(String title, Pageable pageable);
    @Query("SELECT a FROM Game g JOIN g.activeGames a")
    Page<Game.Active> findAllActiveGamesBy(Pageable pageable);
    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE a.id = :id")
    Game.Active findActiveGameById(@Param("id") Long id);
    @Query("SELECT a FROM Game g JOIN g.ratings a WHERE a.id = :id")
    Game.Rating findGameRatingById(@Param("id") Long id);
    Game.Active save(Game.Active active);
    Game.Rating save(Game.Rating rating);
    void deleteById(long id);
    @Query("SELECT g FROM Game g ORDER BY g.lastTimePlayed DESC")
    List<Game> findGamesOrderedByLastTimePlayedDesc(Pageable pageable);
    int countAllByModerationStatus(Game.ModerationStatus moderationStatus);
    boolean existsById(long id);
    boolean existsByTitle(String title);
    boolean existsActiveGameById(long id);
    boolean existsGameRatingById(long id);
}
