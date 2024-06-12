package eu.gaminatorium.game;

import eu.gaminatorium.game.dto.ExampleDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Moduł /application powinien zawierać logikę programu, czyli metody przetrwarzające dane z bazy i od użytkowanika.
 * Fasada (Facade) to wzorzec projektowy, który ukrywa logikę programu - może łączyć kilka metod z kilku różnych serwisów
 * w jeden.
 */

@Service
public class ExampleFacade {

    private final ExampleService exampleService;
    private final ExampleRepository exampleRepository;

    public ExampleFacade(ExampleService exampleService, ExampleRepository exampleRepository) {
        this.exampleService = exampleService;
        this.exampleRepository = exampleRepository;
    }

    ExampleDto alter(long id){
        var exampleGame = exampleRepository.findById(id).orElse(null);
        exampleGame = combinedMethod(exampleGame);
        exampleRepository.save(exampleGame);
        return toDto(exampleGame);
    }

    private ExampleGame combinedMethod(ExampleGame game){
        exampleService.changeTitle(game);
        exampleService.changePublishDate(game);
        return game;
    }

    void create(){
        var newGame = new ExampleGame();
        newGame.setTitle("Generated game");
        newGame.setPublishDate(LocalDate.now());
        exampleRepository.save(newGame);
    }

    private ExampleDto toDto(ExampleGame game){
        return ExampleDto.builder()
                .id(game.getId())
                .title(game.getTitle())
                .publishDate(game.getPublishDate())
                .build();
    }

}
