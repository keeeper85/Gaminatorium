package eu.gaminatorium.game

import eu.gaminatorium.user.User
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class GameSpec extends Specification {

    @Unroll
    def "addRating should create and add new ratings to the set"(){
        given:
        def game = new Game()
        def user1 = new User(id:1L)
        def user2 = new User(id:2L)
        def user3 = new User(id:3L)

        when:
        game.addRating(user1, "testRating1", 1)
        game.addRating(user2, "testRating2", 2)

        then:
        game.ratings.size() == 2
        game.ratings.stream().filter {rating -> (rating.author == user1) }.count() == 1
        game.ratings.stream().filter {rating -> (rating.comment == "testRating1") }.count() == 1
        game.ratings.stream().filter {rating -> (rating.author == user2) }.count() == 1
        game.ratings.stream().filter {rating -> (rating.comment == "testRating2") }.count() == 1
        game.ratings.stream().filter {rating -> (rating.author == user3) }.count() == 0
        game.ratings.stream().filter {rating -> (rating.comment == "testRating3") }.count() == 0
    }

    def "addRating should not allow to post another rating by the same user"(){
        given:
        def game = new Game()
        def user1 = new User(id:1L)

        when:
        game.addRating(user1, "testRating1", 1)

        then:
        !game.addRating(user1, "testRating2", 1)
    }

    def "toggleModerationStatus should change NEW game status to ACCEPTED"(){
        given:
        def game = new Game()

        when:
        game.toggleModerationStatus()

        then:
        game.getModerationStatus() == Game.ModerationStatus.ACCEPTED
    }

    def "toggleModerationStatus should change ACCEPTED game status to PENDING"(){
        given:
        def game = new Game()
        game.toggleModerationStatus()

        when:
        game.toggleModerationStatus()

        then:
        game.getModerationStatus() == Game.ModerationStatus.PENDING
    }

    def "getAverageRating should return the average from score 1 and 3 which is 2.00"(){
        given:
        def game = new Game()
        game.addRating(new User(), "testRating1", 1)
        game.addRating(new User(), "testRating2", 3)

        when:
        String average = game.getAverageRating();

        then:
        average == "2.00"
    }

    def "getAverageRating should return only 2 first decimal digits"(){
        given:
        def game = new Game()
        game.addRating(new User(), "testRating1", 1)
        game.addRating(new User(), "testRating2", 2)
        game.addRating(new User(), "testRating3", 4)

        when:
        String average = game.getAverageRating();

        then:
        average.length() == 4
    }

    def "getAverageRating should return 'N/A' if there are no ratings"(){
        given:
        def game = new Game()

        when:
        String average = game.getAverageRating()

        then:
        average == "N/A"
    }

    def "getRandomRating should not pick the same rating all the time"(){
        given:
        def game = new Game()
        Set<Game.Rating> ratings = new HashSet<>()

        for (i in 0..<1000) {
            User user = new User(id: i)
            Game.Rating rating = new Game.Rating()
            rating.id = i
            rating.comment = "Comment: " + i
            rating.author = user
            ratings.add(rating)
        }
        game.ratings = ratings

        when:
        Set<Game.Rating> randomRatings = new HashSet<>()
        for (int j = 0; j < 100; j++) {
            randomRatings.add(game.getRandomRating())
        }

        then:
        game.ratings.size() == 1000
        randomRatings.size() > 1
    }

    def "startNewGame should not add new activeGame if it is a single player game"(){
        given:
        def game = new Game(maxPlayers: 1)
        def user = new User(id: 1L)

        when:
        game.startNewGame(user)

        then:
        game.activeGames.size() == 0
    }

    def "joinExistingActiveGame should throw IllegalStateException when user wants to join it twice"(){
        given:
        def game = new Game()
        def user = new User()
        Game.Active activeGame = game.startNewGame(user)

        when:
        game.joinExistingActiveGame(activeGame, user)

        then:
        def e = thrown(IllegalStateException)
        e.message == "You're already playing this game"
    }

    def "joinExistingActiveGame should throw IllegalStateException when user wants to join a game that is already full"(){
        given:
        def game = new Game(maxPlayers: 1)
        def user1 = new User(id: 1L)
        def user2 = new User(id: 2L)
        Game.Active activeGame = game.startNewGame(user1)

        when:
        game.joinExistingActiveGame(activeGame, user2)

        then:
        def e = thrown(IllegalStateException)
        e.message == "This game is already full"
    }

    def "leaveExistingActiveGame should throw IllegalStateException when user wants to leave a game they are not playing"(){
        given:
        def game = new Game()
        def user1 = new User(id: 1L)
        def user2 = new User(id: 2L)
        Game.Active activeGame = game.startNewGame(user1)

        when:
        game.leaveExistingActiveGame(activeGame, user2)

        then:
        def e = thrown(IllegalStateException)
        e.message == "This user is not playing that game"
    }

    def "leaveExistingActiveGame should remove the activeGame if the last user leaves it"(){
        given:
        def game = new Game()
        def user1 = new User(id: 1L)
        Game.Active activeGame = game.startNewGame(user1)

        when:
        game.leaveExistingActiveGame(activeGame, user1)

        then:
        !game.activeGames.contains(activeGame)
    }

    def "removeExpiredActiveGames should remove all activeGames after their expiration time"() {
        given:
        def game = new Game(id: 1L, title: "TestGame", maxPlayers: 2)
        def user = new User(id: 1L, userName: "TestUser")
        Game.Active activeGame1 = new Game.Active(EXPIRATION_TIME_MINUTES: -1, host: user)
        Game.Active activeGame2 = new Game.Active(EXPIRATION_TIME_MINUTES: 1, host: user)
        game.activeGames.add(activeGame1)
        game.activeGames.add(activeGame2)

        when:
        game.removeExpiredActiveGames()

        then:
        game.activeGames.size() == 1
        game.activeGames.contains(activeGame2)
    }
}


