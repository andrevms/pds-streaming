package br.com.pds.streaming.config.subscriptionValidation;

import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.authentication.services.UserService;
import br.com.pds.streaming.config.jwt.AuthTokenFilter;
import br.com.pds.streaming.config.jwt.JwtUtils;
import br.com.pds.streaming.domain.subscription.services.SubscriptionService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SubscriptionFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private SubscriptionService subscriptionService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            String username = jwtUtils.getUsernameFromToken(jwt);

            User userDetails = userService.loadUserByUsername(username);

            if (userDetails == null) {
                logger.warn("User not found: {}", username);
                return; // or handle as appropriate
            }

            if (subscriptionService.subscriptionIsExpired(userDetails)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        } catch (JwtException | UsernameNotFoundException e) {
            logger.error("Authentication error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromHeader(request);
    }
}

