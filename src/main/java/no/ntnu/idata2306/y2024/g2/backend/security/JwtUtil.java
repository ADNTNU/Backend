package no.ntnu.idata2306.y2024.g2.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for managing JSON Web Tokens (JWT) in the application.
 * Provides methods to generate, parse, and validate JWTs used for authentication and authorization.
 *
 * @author Daniel Neset
 * @version 17.04.2024
 */
@Component
public class JwtUtil {

  @Value("${jwt_secret_key}")
  private String secretKey;
  private static final String ROLE_KEY = "roles";

  /**
   * Generates a JWT for a given user.
   *
   * @param userDetails The user details for which the token is to be generated.
   * @return Return a signed JWT string.
   */
  public String generateToken(UserDetails userDetails) {
    final long timeNow = System.currentTimeMillis();
    final long millisecondsInHour = 60 * 60 * 1000;
    final long timeAfterOneHour = timeNow + millisecondsInHour;

    return Jwts.builder()
            .subject(userDetails.getUsername())
            .claim(ROLE_KEY, userDetails.getAuthorities())
            .issuedAt(new Date(timeNow))
            .expiration(new Date(timeAfterOneHour))
            .signWith(getSigningKey())
            .compact();
  }

  /**
   * Creates a signing key from the configured secret key.
   *
   * @return Return the {@link SecretKey} used for signing JWTs.
   */
  private SecretKey getSigningKey(){
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
  }

  /**
   * Extracts the username from the given token.
   *
   * @param token The JWT from which the username is extracted.
   * @return Return the username (subject) of the token.
   */
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Validates a token against the user details.
   *
   * @param token The JWT to validate.
   * @param userDetails The user details to validate the token against.
   * @return Return true if the token is valid and not expired, false otherwise.
   * @throws JwtException Throws JwtException if there is a problem parsing the token.
   */
  public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
    final String username = extractUsername(token);
    return userDetails != null
            && username.equals(userDetails.getUsername())
            && !isTokenExpired(token);
  }

  /**
   * Extracts the expiration date of the JWT.
   *
   * @param token The JWT from which the expiration date is to be extracted.
   * @return Return the expiration date of the token.
   */
  private Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Generic method to extract a specific claim from a token.
   *
   * @param token The JWT from which claims are to be extracted.
   * @param claimsResolver A function to process the claims.
   * @param <T> The type of the claim to extract.
   * @return Return the extracted claim.
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the given JWT.
   *
   * @param token The JWT to parse.
   * @return Return the claims contained in the token.
   */
  private Claims extractAllClaims(String token){
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Checks if a JWT has expired.
   *
   * @param token The JWT to check.
   * @return Return true if the token has expired, false otherwise.
   */
  private Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

}
