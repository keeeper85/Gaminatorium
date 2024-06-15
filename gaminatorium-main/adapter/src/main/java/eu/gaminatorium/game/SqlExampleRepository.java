package eu.gaminatorium.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * To jest właściwe repozytorium JPA. Dzięki temu, że rozszerza zwykły interfejs encji zawiera jedynie te metody,
 * które tam zostały zadeklarowane. Spring automatycznie wrzuca to repo do swojego kontekstu i wypełnia nim
 * wszystkie puste pola zwykłego interfejsu encji dzięki magii polimorfizmu.
 *
 * Pamiętaj, że tu muszą być DOKŁADNIE TE SAME METODY CO W REPOZYTORIUM DOMENOWYM!
 */

interface SqlExampleRepository extends ExampleRepository, JpaRepository<ExampleGame, Integer> {

    Optional<ExampleGame> findById(Long id);
    ExampleGame save(ExampleGame game);
    List<ExampleGame> getAllBy();
}
