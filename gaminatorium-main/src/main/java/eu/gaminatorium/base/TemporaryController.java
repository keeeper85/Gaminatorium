package eu.gaminatorium.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TemporaryController {

    @GetMapping
    String hello(){
        return "Welcome to Gaminatorium";
    }
}
