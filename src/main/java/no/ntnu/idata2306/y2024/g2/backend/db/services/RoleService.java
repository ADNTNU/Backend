package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  /**
   * Constructs an instance of UserService with necessary dependency.
   *
   * @param roleRepository The repository handling user data operations.
   */
  @Autowired
  public RoleService(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
  }

  /**
   * Retrieves all users from the database.
   *
   * @return Return a list of {@link User} entities, might be empty if no users are found.
   */
  public List<Role> getAllRoles(){
    List<Role> roles = new ArrayList<>();
    roleRepository.findAll().forEach(roles::add);
    return roles;
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id The unique identifier of the user to retrieve.
   * @return Return an {@link Optional} containing the found user or an empty Optional if no user is found.
   */
  public Optional<Role> getRoleById(int id){
    return roleRepository.findById(id);
  }

  /**
   * Adds a new user to the database.
   *
   * @param role the {@link User} entity to add; must not be null.
   */
  public void addUser(Role role){
    roleRepository.save(role);
  }

  /**
   * Updates an existing user in the database.
   * Note: the user must exist in the database, identified by its ID.
   *
   * @param role The {@link User} entity to update; must not be null.
   */
  public void updateUser(Role role){
    roleRepository.save(role);
  }

  /**
   * Deletes a user from the database.
   *
   * @param role The {@link User} entity to delete; must not be null.
   */
  public void deleteUser(Role role){
    roleRepository.delete(role);
  }

  /**
   * Deletes a user from the database by ID.
   *
   * @param id The unique identifier of the user to delete.
   */
  public void deleteUserById(int id){
    roleRepository.deleteById(id);
  }


}
