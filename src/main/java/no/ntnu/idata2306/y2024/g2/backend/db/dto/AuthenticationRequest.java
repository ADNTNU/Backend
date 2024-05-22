package no.ntnu.idata2306.y2024.g2.backend.db.dto;


/**
 * Data Transfer Object for handling authentication requests.
 * This class is used to encapsulate the user credentials, specifically email and password,
 * that are needed to authenticate a user.
 *
 * @author Daniel Neset
 * @version 18.04.2024
 */
public class AuthenticationRequest {

  private String email;
  private String password;

  /**
   * Default constructor for creating an empty AuthenticationRequest.
   * This is often used by frameworks that require a no-argument constructor.
   */
  public AuthenticationRequest() {
  }

  /**
   * Constructs a new AuthenticationRequest with specified user credentials.
   *
   * @param email    The users email.
   * @param password The user's password.
   */
  public AuthenticationRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /**
   * Returns the email address associated with this authentication request.
   *
   * @return Return the user's email address.
   */
  public String getEmail() {
    return email;
  }


  /**
   * Sets the email for this authentication request.
   *
   * @param email The users email to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the password associated with this authentication request.
   *
   * @return Return the user's password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password for this authentication request.
   *
   * @param password The user's password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

}
