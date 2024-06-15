package eu.gaminatorium.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SqlGameRepository extends GameRepository, JpaRepository<Game, Integer> {

    Game save(Game game);
    List<Game> findAllBy();
    Game findById(long id);
    void deleteById(long id);
    int countAllBy();
    boolean existsById(long id);
}
