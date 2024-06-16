package eu.gaminatorium.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface GameRepository {

    Game save(Game game);
    Page<Game> findAll(Pageable pageable);
    Page<Game.Rating> findAllRatingsByGameId(@Param("gameId") Long gameId, Pageable pageable);
    Page<Game.Active> findAllActiveGamesByGameId(@Param("gameId") Long gameId, Pageable pageable);
    Game findById(long id);
    Game.Active findActiveByTitle(String title);
    Game.Active save(Game.Active active);
    Game.Rating save(Game.Rating rating);
    void deleteById(long id);
    int countAllBy();
    boolean existsById(long id);
}
