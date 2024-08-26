package eu.gaminatorium.user

import eu.gaminatorium.game.Game
import spock.lang.Specification
import spock.lang.Unroll

class UserSpec extends Specification{

    @Unroll
    def "toggle favorite should ADD game from favorite list"(){
        given:
        def user = new User()
        def game = new Game()

        when:
        user.toggleFavoriteGame(game)

        then:
        Set<Game> favoriteGames = user.getFavoriteGames()
        favoriteGames.size() == 1
        favoriteGames.contains(game)
    }

    @Unroll
    def "toggle favorite should REMOVE game from favorite list"(){
        given:
        def user = new User()
        def game = new Game()

        when:
        user.toggleFavoriteGame(game)
        user.toggleFavoriteGame(game)

        then:
        Set<Game> favoriteGames = user.getFavoriteGames()
        favoriteGames.size() == 0
        !favoriteGames.contains(game)
    }

    def "should ADD Active Game to the set"(){
        given:
        def user = new User()
        def activeGame = new Game.Active()

        when:
        user.addCurrentlyPlayedGame(activeGame)

        then:
        Set<Game> currentlyPlayedGames = user.getCurrentlyPlayedGames()
        currentlyPlayedGames.size() == 1
        currentlyPlayedGames.contains(activeGame)
    }

    def "should REMOVE Active Game to the set"(){
        given:
        def user = new User()
        def activeGame = new Game.Active()

        when:
        user.addCurrentlyPlayedGame(activeGame)
        user.removeCurrentlyPlayedGame(activeGame)

        then:
        Set<Game> currentlyPlayedGames = user.getCurrentlyPlayedGames()
        currentlyPlayedGames.size() == 0
        !currentlyPlayedGames.contains(activeGame)
    }
}
