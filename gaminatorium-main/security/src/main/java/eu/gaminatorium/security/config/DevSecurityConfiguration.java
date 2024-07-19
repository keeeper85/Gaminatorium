package eu.gaminatorium.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile(value = {"default", "local"})
@EnableWebSecurity
@AllArgsConstructor
public class DevSecurityConfiguration {

    @Bean
    @Profile(value = {"default", "local"})
    public SecurityFilterChain securityFilterChainDefault(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize ->             // zakomentować/odkomentować jedną z dwóch opcji
//                        authorize
//                                .requestMatchers("/h2-console/**").permitAll()
//                                .anyRequest().permitAll()
//                )
                .authorizeHttpRequests(authorize ->             // zakomentować/odkomentować jedną z dwóch opcji
                        authorize
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()   // w tym wypadku wymagane jest uwierzytelnienie, a testowy uzytkownik został utworzony poniżej
                )
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
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

}
