package eu.gaminatorium.security.providers;

import eu.gaminatorium.security.authentication.GaminatoriumAuthentication;
import eu.gaminatorium.user.User;
import eu.gaminatorium.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "prod")
@RequiredArgsConstructor
public class GaminatoriumAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var currentAuthentication = (GaminatoriumAuthentication) authentication;
        String userName = currentAuthentication.getUsername();
        String password = currentAuthentication.getPassword();

        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User " +
                userName + " not found."));

        if (user.getPassword().equals(password)) {
            currentAuthentication.setAuthenticated(true);
        } else throw new BadCredentialsException("Wrong password");

        return currentAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return GaminatoriumAuthentication.class.isAssignableFrom(authentication);
        return GaminatoriumAuthentication.class.equals(authentication);
    }
}
