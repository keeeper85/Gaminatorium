package eu.gaminatorium.game;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
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
    private Set<Rating> ratings;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Rating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @DecimalMin(value = "0.0", inclusive = true, message = "The score must be at least 0.0")
        @DecimalMax(value = "10.0", inclusive = true, message = "The score must be at most 10.0")
        private double score;
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
