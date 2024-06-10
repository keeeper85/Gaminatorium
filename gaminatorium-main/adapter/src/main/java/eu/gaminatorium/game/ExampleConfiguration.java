package eu.gaminatorium.game;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConfigurationProperties("example")
class ExampleConfiguration {

    private String configuration;

    void print(){
        System.err.println("Test: " + this.configuration);
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
