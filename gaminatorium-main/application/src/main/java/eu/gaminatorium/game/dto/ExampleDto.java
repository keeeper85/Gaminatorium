package eu.gaminatorium.game.dto;

import java.time.LocalDate;

public class ExampleDto {

    private Long id;
    private String title;
    private LocalDate publishDate;

    public ExampleDto(Builder builder) {
        id = builder.id;
        title = builder.title;
        publishDate = builder.publishDate;
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

        public ExampleDto build() {
            return new ExampleDto(this);
        }

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
    }
}
