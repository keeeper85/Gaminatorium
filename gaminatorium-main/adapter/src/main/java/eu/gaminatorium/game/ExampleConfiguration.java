package eu.gaminatorium.game;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Klasy konfiguracyjne oddziaływujące z parametrami z application.properties czy wrzucające potrzebne w danej domenie Beany.
 */

@Configuration
@ConfigurationProperties("example")
@Setter
class ExampleConfiguration {

    private String configuration;

    void print(){
        System.err.println("Test: " + this.configuration);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
