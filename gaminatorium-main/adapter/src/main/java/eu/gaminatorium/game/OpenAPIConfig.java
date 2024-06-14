package eu.gaminatorium.game;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

    @Value("${gaminatorium.openapi.local-url}")
    private String devUrl;

    @Value("${gaminatorium.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("raczkowski.j@hotmail.com");
        contact.setName("Gaminatorium");
        contact.setUrl("https://www.gaminatorium.eu");

        License mitLicense = new License().name("GPL License").url("https://choosealicense.com/licenses/gpl-3.0/");

        Info info = new Info()
                .title("Gaminatorium API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage users and games.").termsOfService("https://wswieciejutra.eu")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
