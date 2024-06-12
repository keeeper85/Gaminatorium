package eu.gaminatorium.game;


import eu.gaminatorium.game.dto.ExampleDto;
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
@RequestMapping("/example")
class ExampleController {

    private final ExampleFacade exampleFacade;
    private final ExampleRepository exampleRepository;
    private final PasswordEncoder passwordEncoder;

    public ExampleController(ExampleFacade exampleFacade, ExampleRepository exampleRepository, PasswordEncoder passwordEncoder) {
        this.exampleFacade = exampleFacade;
        this.exampleRepository = exampleRepository;
        this.passwordEncoder = passwordEncoder;
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

//    @GetMapping("/new")
//    public String create(){
//        exampleFacade.create();
//        return "New example game";
//    }

    @GetMapping("/encode/{text}")
    public String encode(@PathVariable String text){
        return passwordEncoder.encode(text);
    }

}
