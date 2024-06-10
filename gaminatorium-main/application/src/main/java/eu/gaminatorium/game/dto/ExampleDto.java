package eu.gaminatorium.game.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Dto to obiekt transferowy który pobiera tylko te pola encji które są niezbędne do wykonania żądania.
 * Dobrym sposobem na implementację Dto jest użycie wzorca Budowniczy - Builder, który redukuje ilość potrzebnych konstruktorów.
 */

public class ExampleDto {

    static public Builder builder() {
        return new Builder();
    }

    private final Long id;
    private final String title;
    private final LocalDate publishDate;

    public ExampleDto(Long id, String title, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public static class Builder {
        private Long id;
        private String title;
        private LocalDate publishDate;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public ExampleDto build() {
            return new ExampleDto(id, title, publishDate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExampleDto)) return false;
        ExampleDto that = (ExampleDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(publishDate, that.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publishDate);
    }
}
