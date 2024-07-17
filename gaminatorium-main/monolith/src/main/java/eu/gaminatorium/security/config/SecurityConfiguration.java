package eu.gaminatorium.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Profile(value = {"default", "dev", "local"})
    public SecurityFilterChain securityFilterChainDefault(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Profile(value = "prod")
    public SecurityFilterChain securityFilterChainProd(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("gaminatorium")
                .password(passwordEncoder().encode("gampass"))
                .authorities("USER")
                .build();

        userDetailsService.createUser(u1);

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Customizer customizer() {
        return Customizer.withDefaults();
    }
}
