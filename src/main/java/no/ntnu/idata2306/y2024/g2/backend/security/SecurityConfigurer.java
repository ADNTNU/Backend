package no.ntnu.idata2306.y2024.g2.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for configuring HTTP security, authentication, and authorization mechanisms.
 * Enables method-level security and configures endpoints' security requirements.
 *
 * @author Daniel Neset
 * @version 17.04.2024
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfigurer {

  private final UserDetailsService userDetailsService;
  private final JwtRequestFilter jwtRequestFilter;

  /**
   * Constructor to inject user details and JWT filter services.
   *
   * @param userDetailsService The service to load user-specific data.
   * @param jwtRequestFilter   The JWT filter to handle JWT token validation.
   */
  @Autowired
  public SecurityConfigurer(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
    this.userDetailsService = userDetailsService;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  /**
   * Configures the authentication manager builder to use a custom user details service for authentication.
   *
   * @param auth The AuthenticationManagerBuilder to set up the authentication provider.
   * @throws Exception Throws Exception if there's an error during configuration.
   */
  @Autowired
  protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  /**
   * Defines the security filter chain for HTTP requests.
   * Configures CSRF, CORS, session management, and security filters.
   *
   * @param httpSecurity The HttpSecurity to configure.
   * @return Return a configured SecurityFilterChain.
   * @throws Exception Throws Exception if an error occurs during the configuration.
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/authenticate").permitAll())
        .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/").permitAll())
        .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  /**
   * Provides the authentication manager bean from authentication configuration.
   *
   * @param config The authenticationConfiguration to retrieve the authentication manager.
   * @return Return an AuthenticationManager instance.
   * @throws Exception Throws Exception if there's an error retrieving the authentication manager.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Password encoder bean to hash passwords securely.
   *
   * @return Return a BCryptPasswordEncoder instance.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
