package eu.gaminatorium.game

import spock.lang.Specification

class GameSpec extends Specification {

    def "first easy test should return 1"(){
        given:
        int x = 2;

        when:
        x -= 1;

        then:
        x == 1;
    }
}
