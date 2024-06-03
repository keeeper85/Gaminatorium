package eu.gaminatorium.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/hello")
public class TemporaryController {

    @GetMapping()
    ResponseEntity<String> hello(){
         String text = "Welcome to Gaminatorium";
        return ResponseEntity.of(Optional.of(text));
    }
}
