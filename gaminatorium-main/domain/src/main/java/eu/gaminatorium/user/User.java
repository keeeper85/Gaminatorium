package eu.gaminatorium.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import eu.gaminatorium.game.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
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
    private int id;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Set<Game> myFavoriteGames = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Set<Game> gamesAddedByUser = new HashSet<>();

    void toggleFavoriteGame(Game game) {
        if (myFavoriteGames.contains(game)) {
            myFavoriteGames.remove(game);
        }
        else myFavoriteGames.add(game);
    }

    void addNewGame(String title, String description, String tags, String gameUrl, String sourceCodeUrl){
        Game newGame = new Game();
        newGame.setTitle(title);
        newGame.setDescription(description);
        newGame.setGameTags(tags);
        newGame.setGameServiceLink(gameUrl);
        newGame.setSourceCodeLink(sourceCodeUrl);
        gamesAddedByUser.add(newGame);
    }
}
