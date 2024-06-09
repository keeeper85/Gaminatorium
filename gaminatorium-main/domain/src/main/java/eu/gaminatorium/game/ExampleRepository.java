package eu.gaminatorium.game;

import java.util.Optional;

interface ExampleRepository {

    Optional<ExampleGame> findById(Long id);
    ExampleGame save(ExampleGame game);
}
