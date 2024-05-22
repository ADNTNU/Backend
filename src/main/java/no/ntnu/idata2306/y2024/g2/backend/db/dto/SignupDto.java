package no.ntnu.idata2306.y2024.g2.backend.db.dto;

/**
 * Data Transfer Object for user signup.
 * This class encapsulates all the necessary information required to register a new user,
 * including their first name, last name, email, and password.
 *
 * @author Daniel Neset
 * @version 17.04.2024
 */
public class SignupDto {

  private final String firstName;
  private final String lastName;
  private final String email;
  private final String password;

  /**
   * Constructs a new SignupDto with user details.
   *
   * @param firstName The first name of the user.
   * @param lastName  The last name of the user.
   * @param email     The email address of the user.
   * @param password  The password chosen by the user.
   */
  public SignupDto(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  /**
   * Returns the first name of the user.
   *
   * @return Return the users first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns the last name of the user.
   *
   * @return Return the users last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the email  of the user.
   *
   * @return Return the users email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the password of the user.
   *
   * @return Return the user´s password.
   */
  public String getPassword() {
    return password;
  }
}
