package eu.gaminatorium.game;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Serwisy zawierają logikę programu, są pośrednikami pomiędzy encją i kontrolerem. Kontroler powinien odwoływać się do
 * metod w serwisach zamiast samemu brać się za logikę.
 */

@Service
class ExampleService {

    ExampleGame changeTitle(ExampleGame game) {
        game.setTitle("Altered Game #" + game.getId());
        return game;
    }

    ExampleGame changePublishDate(ExampleGame game) {
        game.setPublishDate(LocalDate.of(2023,11, 27));
        return game;
    }
}
