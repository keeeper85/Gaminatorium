package eu.gaminatorium.game;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
class GameWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final GameRepository gameRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        generateGames();
    }

    private void generateGames(){

        Game makao = new Game();
        makao.setTitle("Makao");
        makao.setDescription("A fun multiplayer card game.");
        makao.setGameServiceLink("https://www.gaminatorium.eu/games/makao.png");
        makao.setMaxPlayers(4);
        makao.addTag("card", "multi", "turn-based");
        makao.setReleaseDate(LocalDate.of(2024, 6, 1));
        addRatings(makao);

        gameRepository.save(makao);

        Game mahjong = new Game();
        mahjong.setTitle("Mahjong");
        mahjong.setDescription("An addictve solo puzzle game.");
        mahjong.setGameServiceLink("https://www.gaminatorium.eu/games/mahjong.png");
        mahjong.setMaxPlayers(1);
        mahjong.toggleModerationStatus();
        mahjong.setReleaseDate(LocalDate.of(2024, 5, 31));
        addRatings(mahjong);

        gameRepository.save(mahjong);

        Game tictactoe = new Game();
        tictactoe.setTitle("Tictactoe");
        tictactoe.setDescription("Play this classic game with a friend or against a computer.");
        tictactoe.setGameServiceLink("https://www.gaminatorium.eu/games/tictactoe.png");
        tictactoe.setMaxPlayers(2);
        tictactoe.toggleModerationStatus();
        tictactoe.setTimesPlayedTotal(17);
        tictactoe.setReleaseDate(LocalDate.of(2024, 6, 15));
        addRatings(tictactoe);

        gameRepository.save(tictactoe);

    }

        private void addRatings(Game game){
            for (int i = 0; i < getRandomInt(); i++) {
                game.addRating(getRandomComment(), getRandomInt(), getRandomDate());
            }
        }

        private static LocalDate getRandomDate(){
            return LocalDate.of(2024, getRandomInt(), getRandomInt() + getRandomInt());
        }

        private static String getRandomComment(){
            List<String> comments = List.of("shitty", "boring", "mediocre", "nothing special", "okay", "nice", "exciting", "great", "awesome");
            return comments.get(getRandomInt() - 1);
        }

        private static int getRandomInt(){
            return ThreadLocalRandom.current().nextInt(1,10);
        }

}
