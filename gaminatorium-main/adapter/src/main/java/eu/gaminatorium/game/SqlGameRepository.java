package eu.gaminatorium.game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlGameRepository extends GameRepository, JpaRepository<Game, Integer> {

    Game save(Game game);

}
