package br.com.pds.streaming.config.security;

import br.com.pds.streaming.config.jwt.AuthEntryPointJwt;
import br.com.pds.streaming.config.jwt.AuthTokenFilter;
import br.com.pds.streaming.config.subscriptionValidation.SubscriptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SubscriptionFilter subscriptionFilter() {
        return new SubscriptionFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationRequests -> {
            authorizationRequests
                    .requestMatchers("/auth/signin", "/auth/signup").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/users", "/api/archives", "/api/files").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/api/movies", "/api/episodes", "/api/seasons", "/api/tvshows", "/api/tv-shows", "/api/tv_shows", "/api/ask-llm-quiz", "/api/ask-llm", "/api/ask-llm-quiz").hasAnyRole("ADMIN", "USER_PREMIUM")
//                    .requestMatchers(HttpMethod.POST, "/api/archives", "/api/files", "/api/movies", "/api/episodes", "/api/seasons", "/api/tvshows", "/api/tv-shows", "/api/tv_shows").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                    .requestMatchers(HttpMethod.PUT, "/api/movies", "/api/episodes", "/api/seasons", "/api/tvshows", "/api/tv-shows", "/api/tv_shows").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/api/users").permitAll()
//                    .requestMatchers(HttpMethod.PATCH, "/api/movies", "/api/episodes", "/api/seasons", "/api/tvshows", "/api/tv-shows", "/api/tv_shows").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PATCH, "/api/users").permitAll()
//                    .requestMatchers(HttpMethod.DELETE, "/api/archives", "/api/files", "/api/movies", "/api/episodes", "/api/seasons", "/api/tvshows", "/api/tv-shows", "/api/tv_shows").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/subscriptions").hasAnyRole("ADMIN", "USER_PREMIUM", "PENDING_USER")
                    .anyRequest().permitAll();
        });
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(subscriptionFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
