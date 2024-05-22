package no.ntnu.idata2306.y2024.g2.backend.db.services;

import java.io.IOException;
import java.util.Optional;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import no.ntnu.idata2306.y2024.g2.backend.security.AccessUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


/**
 * Service class for managing access and authentication of {@link User} entities.
 * Implements {@link UserDetailsService} for integration with Spring Security.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class AccessUserService implements UserDetailsService {

  private static final int MIN_PASSWORD_LENGTH = 8;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  /**
   * Constructs an instance of AccessUserService with necessary dependency.
   *
   * @param userRepository The repository handling user operations.
   * @param roleRepository The repository handling role operations.
   */
  @Autowired
  public AccessUserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  /**
   * Loads user-specific data by username.
   *
   * @param username The username identifying the user whose data is to be loaded.
   * @return Return UserDetails containing the user's information.
   * @throws UsernameNotFoundException Throws UsernameNotFoundException if the user is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByEmail(username);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("USer: " + username + " not found!");
    }
  }

  /**
   * Retrieves the {@link User} from the current security session.
   *
   * @return Return the authenticated {@link User} entity or null if not authenticated.
   */
  public User getSessionUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = authentication.getName();
    return userRepository.findUserByEmail(username).orElse(null);
  }

  /**
   * Checks if a user with the specified username exists.
   *
   * @param username The username to check.
   * @return Return true if the user exists, false otherwise.
   */
  private boolean userExists(String username) {
    try {
      loadUserByUsername(username);
      return true;
    } catch (UsernameNotFoundException usernameNotFoundException) {
      return false;
    }
  }

  /**
   * Attempts to create a new user with the specified username and password.
   *
   * @param username The username for the new user.
   * @param password The password for the new user.
   * @throws IOException Throws IOException if there is an error in creating
   *      the user or if validation fails.
   */
  public void tryCreateNewUser(String username, String password) throws IOException {
    String errorMessage;
    if ("".equals(username)) {
      errorMessage = "Username cant be empty";
    } else if (userExists(username)) {
      errorMessage = "Username already exists";
    } else {
      errorMessage = checkPasswordRequirements(password);
      if (errorMessage == null) {
        createUser(username, password);
      }
    }
    if (errorMessage != null) {
      throw new IOException(errorMessage);
    }
  }

  /**
   * Validates the password against specific requirements.
   *
   * @param password The password to check.
   * @return Return an error message if validation fails, null if it passes.
   */
  private String checkPasswordRequirements(String password) {
    String errorMessage = null;
    if (password == null || password.length() == 0) {
      errorMessage = "Password cannot be empty";
    } else if (password.length() < MIN_PASSWORD_LENGTH) {
      errorMessage = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
    }
    return errorMessage;
  }

  /**
   * Creates a new user with the specified username and hashed password, assigns a default role.
   *
   * @param username The username for the new user.
   * @param password The password for the new user.
   */
  private void createUser(String username, String password) {
    Role userRole = roleRepository.findOneByName("ROLE_USER");
    if (userRole != null) {
      User user = new User("firstName", "LastName", username, createHash(password));
      user.addRole(userRole);
      userRepository.save(user);
    }
  }

  /**
   * Creates a bcrypt hash of the given password.
   *
   * @param password The password to hash.
   * @return Return the hashed password.
   */
  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

}
