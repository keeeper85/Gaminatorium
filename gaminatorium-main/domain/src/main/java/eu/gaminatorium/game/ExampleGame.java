package eu.gaminatorium.game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDate;

/**
 * Standardowa encja, tu nie trzeba wiele kombinować, nadajemy żądane pola i ustawiamy walidacje.
 */

@Entity
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
