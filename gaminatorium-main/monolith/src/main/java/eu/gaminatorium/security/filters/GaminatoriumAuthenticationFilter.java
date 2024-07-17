package eu.gaminatorium.security.filters;

import eu.gaminatorium.security.authentication.GaminatoriumAuthentication;
import eu.gaminatorium.security.managers.GaminatoriumAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class GaminatoriumAuthenticationFilter extends OncePerRequestFilter {

    private final GaminatoriumAuthenticationManager manager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String userName = request.getHeader("username");
        String password = request.getHeader("password");

        var authentication = new GaminatoriumAuthentication(userName, password);

        var managerAuthentication = manager.authenticate(authentication);

        if (managerAuthentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(managerAuthentication);

            filterChain.doFilter(request, response);
        }

    }
}
