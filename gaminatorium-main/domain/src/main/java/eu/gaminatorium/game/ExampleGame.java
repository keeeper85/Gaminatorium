package eu.gaminatorium.game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDate;

/**
 * Standardowa encja, tu nie trzeba wiele kombinować, nadajemy żądane pola i ustawiamy walidacje.
 */

@Entity
@Getter
@Setter
class ExampleGame {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private LocalDate publishDate;

    @PersistenceConstructor
    public ExampleGame() {
        title = "Example Title";
        publishDate = LocalDate.now();
    }
}
