package eu.gaminatorium.game;

import org.springframework.data.repository.Repository;

import java.util.Optional;


interface SqlExampleRepository extends ExampleRepository, Repository<ExampleGame, Integer> {

    Optional<ExampleGame> findById(Long id);
    ExampleGame save(ExampleGame game);
}
