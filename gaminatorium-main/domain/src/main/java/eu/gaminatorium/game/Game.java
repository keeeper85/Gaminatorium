package eu.gaminatorium.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.gaminatorium.user.User;
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
@Entity
@Table(name="games")
public class Game {

    public enum ModerationStatus { PENDING, ACCEPTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min=3, max=30, message="Game title must be 3-30 characters long.")
    private String title;

    @Size(min=10, max=200, message="Game description must be 10-200 characters long.")
    private String description;

    private String gameTags = "";

    @URL(message="Invalid URL format")
    private String gameServiceUrl;

    @URL(message="Invalid URL format")
    private String sourceCodeUrl;

    @Column(name = "moderation_status")
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;

    @Min(value=1, message="Number must be a positive integer type")
    private int maxPlayers;

    @Min(value=0, message="Number must be a positive long type")
    private long timesPlayedTotal;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate releaseDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime lastTimePlayed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    @JsonIgnore
    private User author;

    @OneToMany(mappedBy="game", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy="game", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private Set<Active> activeGames = new HashSet<>();

    public boolean addRating(User user, String comment, int score) {
        //todo: test this method with adding comments from different users
        if (ratings.stream().filter(rating -> rating.author != user).toList().size() > 1) return false;

        Rating rating = new Rating();
        rating.setComment(comment);
        rating.setScore(score);
        rating.setPostingDate(LocalDate.now());
        rating.setGame(this);
        rating.setAuthor(user);
        ratings.add(rating);
        user.getRatings().add(rating);

        System.err.println("TRUE");
        return true;
    }

    public void toggleModerationStatus() {
        if (moderationStatus == ModerationStatus.PENDING) {
            moderationStatus = ModerationStatus.ACCEPTED;
            releaseDate = LocalDate.now();
        } else {
            moderationStatus = ModerationStatus.PENDING;
        }
    }

    public void addTag(String tag, String... tags) {
        StringBuilder builder = new StringBuilder(gameTags);
        builder.append(tag).append(" ");
        for (String s : tags) {
            builder.append(s).append(" ");
        }
        gameTags = builder.toString().trim();
    }

    public String getAverageRating() {
        if (ratings.isEmpty()) return "N/A";
        double averageRating = ratings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
        return String.format("%.2f", averageRating);
    }

    public Rating getRandomRating() {
        return ratings.stream().findAny().orElse(null);
    }

    public boolean deleteGameRating(Rating rating) {
        if (ratings.remove(rating)) {
            rating.getAuthor().getRatings().remove(rating);
            return true;
        }
        return false;
    }

    public Active startNewGame(User host) {
        Active activeGame = new Active();
        activeGame.setGame(this);
        activeGame.setHost(host);
        activeGame.addPlayer(host);
        activeGames.add(activeGame);
        lastTimePlayed = LocalDateTime.now();
        return activeGame;
    }

    public void joinExistingActiveGame(Active activeGame, User player) {
        if (activeGame.getCurrentPlayers() < this.maxPlayers) {
            activeGame.addPlayer(player);
            player.getCurrentlyPlayedGames().add(activeGame);
        } else {
            throw new IllegalStateException("This game is already full");
        }
    }

    public void leaveExistingActiveGame(Active activeGame, User player) {
        activeGame.removePlayer(player);
        player.getCurrentlyPlayedGames().remove(activeGame);
        if (activeGame.getPlayers().isEmpty()) {
            activeGames.remove(activeGame);
        }
    }

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name="game_rating", uniqueConstraints={
            @UniqueConstraint(columnNames={"game_id", "user_id"})
    })
    public static class Rating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Min(value=1, message="The score must be at least 1")
        @Max(value=10, message="The score must be at most 10")
        private int score;

        @NotBlank(message="Comment cannot be blank")
        private String comment;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
        private LocalDate postingDate;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="game_id")
        @JsonIgnore
        private Game game;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="user_id")
        @JsonIgnore
        private User author;
    }

    @Entity
    @Table(name="game_active")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Active {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Min(value=1, message="Number of current players must be positive.")
        private int currentPlayers = 1;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
        private LocalDateTime startedAt = LocalDateTime.now();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="game_id")
        @JsonIgnore
        private Game game;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="user_id")
        @JsonIgnore
        private User host;

        @ManyToMany
        @JoinTable(
                name="active_game_players",
                joinColumns=@JoinColumn(name="active_game_id"),
                inverseJoinColumns=@JoinColumn(name="user_id")
        )
        private Set<User> players = new HashSet<>();

        public void addPlayer(User user) {
            players.add(user);
            currentPlayers = players.size();
        }

        public void removePlayer(User user) {
            players.remove(user);
            currentPlayers = players.size();
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
