package eu.gaminatorium.game;

import java.util.Optional;

/**
 * Tutaj definiujemy metody do wybierania danych z bazy. W odróżnieniu od architektury MVC tutaj nie łączymy się
 * jeszcze z bazą (brak @Repository albo implementacji JPA) - do tego dojdzie w module /adapter.
 */

interface ExampleRepository {

    Optional<ExampleGame> findById(Long id);
    ExampleGame save(ExampleGame game);
}
