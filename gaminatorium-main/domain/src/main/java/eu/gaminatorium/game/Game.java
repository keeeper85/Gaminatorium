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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity public class Game {

    public enum ModerationStatus {PENDING, ACCEPTED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=3, max = 30, message = "Game title must be 3-30 characters long.")
    private String title;
    @Size(min=10, max = 200, message = "Game description must be 10-200 characters long.")
    private String description;
    private String gameTags = "";
    @URL(message = "Invalid URL format")
    private String gameServiceLink;
    @URL(message = "Invalid URL format")
    private String sourceCodeLink;
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;
    @Min(value = 1, message = "Number must be a positive integer type")
    private int maxPlayers;
    @Min(value = 0, message = "Number must be a positive long type")
    private long timesPlayedTotal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Active> activeGames = new HashSet<>();

    void addRating(String comment, int score, LocalDate postingDate){
        Rating rating = new Rating();
        rating.setComment(comment);
        rating.setScore(score);
        rating.setPostingDate(postingDate);
        rating.setGame(this);
        ratings.add(rating);
    }

    void toggleModerationStatus(){
        if (moderationStatus == ModerationStatus.PENDING) {
            moderationStatus = ModerationStatus.ACCEPTED;
            releaseDate = LocalDate.now();
        }
        else moderationStatus = ModerationStatus.PENDING;
    }

    void addTag(String tag, String... tags) {
        StringBuilder builder = new StringBuilder(gameTags);
        builder.append(tag).append(" ");
        if (tags.length > 0){
            for (String s : tags) {
                builder.append(s).append(" ");
            }
        }
        gameTags = builder.toString().trim();
    }

    String getAverageRating(){
        if (ratings.isEmpty()) return "N/A";
        double averageRating = 0.0;

        for (Rating rating : ratings) {
            averageRating += rating.score;
        }
        averageRating = averageRating / ratings.size();
        return Math.round(averageRating * 100.0) / 100.0 + "";
    }

    Game.Rating getRandomRating(){
        return ratings.stream().findFirst().get();
    }

    void addRating(int score, String comment){
        Rating rating = new Rating();
        rating.setGame(this);
        rating.setScore(score);
        rating.setComment(comment);
        rating.setPostingDate(LocalDate.now());
        ratings.add(rating);
    }

    Game.Active startNewGame(){
        var activeGame = new Game.Active();
        activeGame.setGame(this);
        activeGames.add(activeGame);
        return activeGame;
    }

    void joinExistingActiveGame(Game.Active activeGame){
        if (activeGame.currentPlayers < activeGame.getGame().maxPlayers) activeGame.currentPlayers++;
        if (activeGame.currentPlayers >= activeGame.getGame().maxPlayers) activeGames.remove(activeGame);
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

    @Entity
    @Table(name = "game_active")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Active {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Min(value = 1, message = "Number of current players must be positive.")
        private int currentPlayers = 1;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
        private LocalDateTime startedAt = LocalDateTime.now();
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "game_id")
        @JsonBackReference
        private Game game;

    }
}
