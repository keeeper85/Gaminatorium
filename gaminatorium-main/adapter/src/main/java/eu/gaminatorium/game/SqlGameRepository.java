package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface SqlGameRepository extends GameRepository, JpaRepository<Game, Integer> {

    Game save(Game game);
    Page<Game> findAll(Pageable pageable);
    @Query("SELECT r FROM Game g JOIN g.ratings r WHERE g.id = :gameId")
    Page<Game.Rating> findAllRatingsByGameId(@Param("gameId") Long gameId, Pageable pageable);
    @Query("SELECT a FROM Game g JOIN g.activeGames a WHERE a.game.id = :gameId")
    Page<Game.Active> findAllActiveGamesByGameId(@Param("gameId") Long gameId, Pageable pageable);
    Game findById(long id);
    Page<Game> findAllByTitle(String title, Pageable pageable);
    Page<Game.Active> findActiveByTitle(String title, Pageable pageable);
    Game.Active save(Game.Active active);
    Game.Rating save(Game.Rating rating);
    void deleteById(long id);
    int countAllBy();
    boolean existsById(long id);
}
