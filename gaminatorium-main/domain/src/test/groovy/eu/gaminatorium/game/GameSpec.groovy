package eu.gaminatorium.game

import eu.gaminatorium.user.User
import spock.lang.Specification
import spock.lang.Unroll

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
}
