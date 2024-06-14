package eu.gaminatorium.game;


import eu.gaminatorium.game.dto.ExampleDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Klasyczny kontroler REST, starajmy się tworzyć krótkie metody z logiką zawartą w serwisach.
 * Dla łapania wyjątków można stworzyć pakiet /exceptions
 * Dla filtracji requestów można stworzyć pakiet /fiters
 */

@RestController
@Tag(name = "Test Controller", description = "A few random methods for testing")
@RequestMapping("/example")
class ExampleController {

    private final ExampleFacade exampleFacade;
    private final ExampleRepository exampleRepository;

    public ExampleController(ExampleFacade exampleFacade, ExampleRepository exampleRepository) {
        this.exampleFacade = exampleFacade;
        this.exampleRepository = exampleRepository;
    }

    @GetMapping("/test")
    public String test(){
        return "To jest test";
    }

    @GetMapping("/getfirst")
    public ResponseEntity<ExampleGame> getExampleGame(){
        return ResponseEntity.of(Optional.of(exampleRepository.findById(1l).get()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExampleGame>> getAllGames(){
        return ResponseEntity.ok(exampleRepository.getAllBy());
    }

    @GetMapping("/alter/{id}")
    public ResponseEntity<ExampleDto> alterExampleGame(@PathVariable long id){
        return ResponseEntity.ok(exampleFacade.alter(id));
    }


}
