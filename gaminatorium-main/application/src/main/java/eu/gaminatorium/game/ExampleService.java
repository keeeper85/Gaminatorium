package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ExampleDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
class ExampleService {

    ExampleGame changeTitle(ExampleGame game) {
        game.setTitle("Very new title");
        return game;
    }

    ExampleGame changePublishDate(ExampleGame game) {
        game.setPublishDate(LocalDate.of(2023,11,27));
        return game;
    }
}
