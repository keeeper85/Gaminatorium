package eu.gaminatorium.game;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
class ExampleController {

    private final ExampleFacade exampleFacade;
    private final SqlExampleRepository sqlExampleRepository;
    private ExampleGame game = new ExampleGame();

    public ExampleController(ExampleFacade exampleFacade, SqlExampleRepository sqlExampleRepository) {
        this.exampleFacade = exampleFacade;
        this.sqlExampleRepository = sqlExampleRepository;
    }

    @GetMapping("/test")
    public String test(){
        return "To jest test";
    }

    @GetMapping("/example")
    public ResponseEntity<ExampleGame> getExampleGame(){
        return ResponseEntity.of(Optional.of(game));
    }

    @GetMapping("/alter")
    public ResponseEntity<ExampleGame> alterExampleGame(){
        var exampleGame = exampleFacade.combinedMethod(game);
        return ResponseEntity.of(Optional.of(exampleGame));
    }

}
