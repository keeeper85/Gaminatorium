package eu.gaminatorium.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import eu.gaminatorium.game.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", unique = true)
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game lastGamePlayed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorite_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonBackReference
    private Set<Game> favoriteGames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="author", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Game> gamesAddedByUser = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Game.Rating> ratings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "host", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Game.Active> hostedGames = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    private Set<Game.Active> currentlyPlayedGames = new HashSet<>();

    void toggleFavoriteGame(Game game) {
        if (favoriteGames.contains(game)) {
            favoriteGames.remove(game);
        }
        else favoriteGames.add(game);
    }

    void addNewGame(String title, String description, String tags, String gameUrl, String sourceCodeUrl){
        Game newGame = new Game();
        newGame.setTitle(title);
        newGame.setDescription(description);
        newGame.setGameTags(tags);
        newGame.setGameServiceUrl(gameUrl);
        newGame.setSourceCodeUrl(sourceCodeUrl);
        newGame.setAuthor(this);
        gamesAddedByUser.add(newGame);
    }

    public void addCurrentlyPlayedGame(Game.Active activegame){
        currentlyPlayedGames.add(activegame);
    }

    public void removeCurrentlyPlayedGame(Game.Active activegame){
        currentlyPlayedGames.remove(activegame);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
