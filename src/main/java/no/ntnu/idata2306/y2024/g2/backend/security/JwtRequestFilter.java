package no.ntnu.idata2306.y2024.g2.backend.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT request filter to authenticate requests based on token.
 * It checks the Authorization header for a JWT, validates it, and sets the security context if the token is valid.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class.getSimpleName());
  private final UserDetailsService userDetailsService;
  private final  JwtUtil jwtUtil;

  /**
   * Constructs the JWT request filter with necessary dependencies.
   *
   * @param userDetailsService The service to load user-specific data.
   * @param jwtUtil The utility to handle JWT operations such as validation and extraction.
   */
  @Autowired
  public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil){
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  /**
   * Method that filters each request to check for JWT in the Authorization header and authenticate the user.
   *
   * @param request The HTTP request.
   * @param response the HTTP response.
   * @param filterChain the filter chain.
   * @throws ServletException Throws ServletException if a servlet-specific error occurs.
   * @throws IOException Throws IOException if an I/O error occurs.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = getJwtToken(request);
    String username = jwtToken != null ? getUsernameFrom(jwtToken) : null;

    if(username != null && notAuthenticatedYet()) {
      UserDetails userDetails = getUserDetailsFromDatabase(username);
      if(jwtUtil.validateToken(jwtToken, userDetails)){
        registerUserAsAuthenticated(request, userDetails);
      }
    }
    filterChain.doFilter(request, response);
  }

  private UserDetails getUserDetailsFromDatabase(String username){
    UserDetails userDetails = null;
    try{
      userDetails = userDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException usernameNotFoundException){
      logger.warn("User: " + username + " Not found in the database.");
    }
    return userDetails;
  }

  private String getJwtToken(HttpServletRequest request){
    final String authorizationHeader = request.getHeader("Authorization");
    String jwt = null;
    if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      jwt = stripBearerPrefixFrom(authorizationHeader);
    }
    return jwt;
  }

  private static String stripBearerPrefixFrom(String authorizationHeaderValue){
    final int numberOfCharsToStrip = "Bearer ".length();
    return authorizationHeaderValue.substring(numberOfCharsToStrip);
  }

  private String getUsernameFrom(String jwtToken){
    String username = null;
    try{
      username = jwtUtil.extractUsername(jwtToken);
    }catch (MalformedJwtException malformedJwtException){
      logger.warn("Malformed JWT: " + malformedJwtException.getMessage());
    }catch (JwtException jwtException){
      logger.warn("Error in JWT token: " + jwtException.getMessage());
    }
    return username;
  }

  private boolean notAuthenticatedYet(){
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private static void registerUserAsAuthenticated(HttpServletRequest request, UserDetails userDetails){
    final UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(upat);
  }

}
