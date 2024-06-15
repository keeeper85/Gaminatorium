package eu.gaminatorium.game;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity public class Game {

    public enum Type {SOLO, MULTI, MULTI_SOLO}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=3, max = 30, message = "Game title must be 3-30 characters long.")
    private String title;
    @Size(min=10, max = 200, message = "Game description must be 10-200 characters long.")
    private String description;
    @URL(message = "Invalid URL format")
    private String link;
    private Type type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Rating> ratings = new HashSet<>();

    void addRating(String comment, int score, LocalDate postingDate){
        Rating rating = new Rating();
        rating.setComment(comment);
        rating.setScore(score);
        rating.setPostingDate(postingDate);
        rating.setGame(this);
        ratings.add(rating);
    }

    @Entity
    @Table(name = "game_rating")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Min(value = 1, message = "The score must be at least 1")
        @Max(value = 10, message = "The score must be at most 10")
        private int score;
        @NotBlank(message = "Comment cannot be blank")
        private String comment;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private LocalDate postingDate;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "game_id")
        @JsonBackReference
        private Game game;
    }
}
