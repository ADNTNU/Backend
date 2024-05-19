package no.ntnu.idata2306.y2024.g2.backend.db.dto;

/**
 * Data Transfer Object for encapsulating the authentication response.
 * This class holds the JWT (JSON Web Token) used for client authentication following a successful login.
 * The JWT can then be used by the client to make authenticated requests to the server.
 *
 * @author Daniel Neset
 * @version 17.04.2024
 */
public class AuthenticationResponse {

  private final String jwt;

  /**
   * Constructs a new AuthenticationResponse with the specified JWT.
   *
   * @param jwt The JSON Web Token generated during the authentication process.
   */
  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }


  /**
   * Returns the JSON Web Token associated with the current authentication session.
   *
   * @return Return the JWT string.
   */
  public String getJwt() {
    return jwt;
  }

}
