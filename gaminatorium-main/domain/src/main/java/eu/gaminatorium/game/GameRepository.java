package eu.gaminatorium.game;

import java.util.List;

public interface GameRepository {

    Game save(Game game);
    List<Game> findAllBy();
    Game findById(long id);
    void deleteById(long id);
    int countAllBy();
    boolean existsById(long id);
}
