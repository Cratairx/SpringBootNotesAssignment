package org.example.springbootnotesassignment.config;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Körs när någon har loggat in med Microsoft
    @Bean
    public AuthenticationSuccessHandler entraSuccessHandler(AppUserRepository repo) {
        return (request, response, authentication) -> {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String username = oidcUser.getPreferredUsername();

            // Skapa användaren i databasen första gången
            AppUser user = repo.findByUsername(username).orElseGet(() -> {
                AppUser u = new AppUser();
                u.setUsername(username);
                u.setPassword("ENTRA-USER"); // inget riktigt lösenord
                u.setRole("USER");
                return repo.save(u);
            });

            // Samma session-värden som vanliga inloggningen
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            response.sendRedirect("ADMIN".equals(user.getRole()) ? "/adminpage" : "/success");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationSuccessHandler entraSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/style.css").permitAll()
                        .requestMatchers("/", "/register", "/success", "/login", "/notes", "/shownotes",
                                "/editnote", "/delete", "/adminpage", "/showUserNotes", "/changepassword",
                                "/adminnotes", "/adminchangepassword").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .successHandler(entraSuccessHandler)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}