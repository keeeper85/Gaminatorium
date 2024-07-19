package eu.gaminatorium.security.credentials;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class GaminatoriumAuthorities implements GrantedAuthority {

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
