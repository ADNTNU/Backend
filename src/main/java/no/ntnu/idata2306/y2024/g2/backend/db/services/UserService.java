package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 * Provides methods for CRUD operations on {@link User}
 * entities through {@link UserRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final SavedService savedService;

  /**
   * Constructs an instance of UserService with necessary dependency.
   *
   * @param userRepository The repository handling user data operations.
   */
  @Autowired
  public UserService(UserRepository userRepository, SavedService savedService) {
    this.userRepository = userRepository;
    this.savedService = savedService;
  }

  /**
   * Retrieves all users from the database.
   *
   * @return Return a list of {@link User} entities, might be empty if no users are found.
   */
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id The unique identifier of the user to retrieve.
   * @return Return an {@link Optional} containing the found user or an empty Optional if no user is found.
   */
  public Optional<User> getUserById(int id) {
    return userRepository.findById(id);
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id The unique identifier of the user to retrieve.
   * @return Return an {@link Optional} containing the found user or an empty Optional if no user is found.
   */
  public Optional<User> getUserByEmail(String email){
    return userRepository.findUserByEmail(email);
  }

  /**
   * Adds a new user to the database.
   *
   * @param user the {@link User} entity to add; must not be null.
   */
  public void addUser(User user) {
    userRepository.save(user);
  }

  /**
   * Updates an existing user in the database.
   * Note: the user must exist in the database, identified by its ID.
   *
   * @param user The {@link User} entity to update; must not be null.
   */
  public void updateUser(User user) {
    userRepository.save(user);
  }

  /**
   * Deletes a user from the database.
   *
   * @param user The {@link User} entity to delete; must not be null.
   */
  public void deleteUser(User user) {
    userRepository.delete(user);
  }


  /**
   * Deletes a user from the database by ID.
   *
   * @param id The unique identifier of the user to delete.
   */
  public void deleteUserById(int id) {
    savedService.deleteUserById(id);
    userRepository.deleteById(id);
  }

}
