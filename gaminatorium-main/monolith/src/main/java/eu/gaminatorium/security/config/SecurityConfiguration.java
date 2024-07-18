package eu.gaminatorium.security.config;

import eu.gaminatorium.security.filters.GaminatoriumAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final GaminatoriumAuthenticationFilter gaminatoriumAuthenticationProvider;

    @Bean
    @Profile(value = {"default", "local"})
    public SecurityFilterChain securityFilterChainDefault(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    @Profile(value = "prod")
    public SecurityFilterChain securityFilterChainProd(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(gaminatoriumAuthenticationProvider, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/login.html").permitAll()
                                .anyRequest().authenticated()
                )
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

}
