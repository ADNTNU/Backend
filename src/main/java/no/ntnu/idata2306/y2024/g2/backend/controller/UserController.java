package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.RoleUserDTO;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.RoleService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a rest controller for locations entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset and Anders Lund
 * @version 18.05.2024
 */
@RestController
@RequestMapping("users")
@Tag(name = "User API")
@CrossOrigin
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final RoleService roleService;

  /**
   * Constructs an instance of UserController with necessary dependency.
   *
   * @param userService The Service handling users operations.
   * @param roleService The Service handling role operations.
   */
  @Autowired
  public UserController(UserService userService, RoleService roleService){
    this.userService = userService;
    this.roleService = roleService;
  }

  /**
   * Return all Users
   *
   * @return Return all users.
   */
  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @JsonView(Views.hidePassword.class)
  @Operation(summary = "Get all Users",
          description = "Return all users. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Users returned."),
          @ApiResponse(responseCode = "204", description = "No users found.", content = @Content)
  })
  public ResponseEntity<List<User>> getAll() {
    ResponseEntity<List<User>> response;
    List<User> users = new ArrayList<>(userService.getAllUsers());
    if (users.isEmpty()) {
      logger.warn("There is no Users, not found.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all users.");
      response = new ResponseEntity<>(users, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Retrieves a single user by ID.
   *
   * @param id The ID of the user to retrieve.
   * @return Return the user if found, otherwise returns not found status.
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Get a single user.", description = "Get a single JSON object with the user. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The user return in the response body."),
          @ApiResponse(responseCode = "404", description = "The user are not available, not found.", content = @Content)
  })
  @JsonView(Views.hidePassword.class)
  public ResponseEntity<User> getOne(@PathVariable Integer id) {
    ResponseEntity<User> response;
    Optional<User> user = userService.getUserById(id);
    if(user.isPresent()){
      logger.info("Returning a single user.");
      response = new ResponseEntity<>(user.get(), HttpStatus.OK);
    }else{
      logger.warn("There is no user with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a role to a user.
   *
   * @param roleUserDTO DTO containing user and role information.
   * @return Return responseEntity with the ok or a not found.
   */
  @PostMapping("/addRole")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a role to a user",
          description = "Add a role to a user. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User and Role found and updated."),
          @ApiResponse(responseCode = "404", description = "User or Role is not found.", content = @Content)
  })
  public ResponseEntity<RoleUserDTO> addRole(@RequestBody RoleUserDTO roleUserDTO){
    ResponseEntity<RoleUserDTO> response;
    Optional<User> user = userService.getUserById(roleUserDTO.getUser().getId());
    Optional<Role> role = roleService.getRoleById(roleUserDTO.getRole().getId());
    if(user.isPresent() && role.isPresent()){
      logger.info("Add a role to an user.");
      user.get().addRole(role.get());
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      logger.warn("Cant find user and role.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Removes a role from a user.
   *
   * @param roleUserDTO DTO containing user and role information.
   * @return ResponseEntity with appropriate status.
   */
  @PostMapping("/removeRole")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Remove a role to a user",
          description = "Remove a role to a user. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User and Role found and updated."),
          @ApiResponse(responseCode = "404", description = "User or Role is not found.", content = @Content)
  })
  public ResponseEntity<RoleUserDTO> removeRole(@RequestBody RoleUserDTO roleUserDTO){
    ResponseEntity<RoleUserDTO> response;
    Optional<User> user = userService.getUserById(roleUserDTO.getUser().getId());
    Optional<Role> role = roleService.getRoleById(roleUserDTO.getRole().getId());

    if(user.isPresent() && role.isPresent()){
      logger.info("Remove a role to an user.");
      user.get().removeRole(role.get());
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      logger.warn("Cant find user and role.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Deletes a user by ID.
   *
   * @param id The ID of the user to delete.
   * @return Return ResponseEntity with the operation result.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value ={
          @ApiResponse(responseCode = "200", description = "User deleted successfully"),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @JsonView(Views.hidePassword.class)
  public ResponseEntity<Optional<User>> deleteUser(@PathVariable Integer id) {
    ResponseEntity<Optional<User>> response;
    Optional<User> user = userService.getUserById(id);
    if (user.isPresent()) {
      logger.info("Delete user.");
      userService.deleteUserById(id);
      response = new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      logger.warn("Could not find user.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Updates a user.
   *
   * @param id The ID of the user to update.
   * @param updatedUser The updated user information.
   * @return Return responseEntity with the updated user.
   */
  @PutMapping("/{id}")
  @JsonView(Views.hidePassword.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing User",
          description = "Updates a user by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The User is updated."),
          @ApiResponse(responseCode = "400", description = "No User invalid.", content = @Content),
          @ApiResponse(responseCode = "404", description = "User is found.", content = @Content)
  })
  public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser){
    ResponseEntity<User> response;
    Optional<User> existingUser = userService.getUserById(id);
    if (existingUser.isEmpty()) {
      logger.warn("Cannot find the user from id");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!updatedUser.isValid()){
      logger.warn("User is invalid.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else{
      logger.info("Update user.");
      updatedUser.setId(existingUser.get().getId());
      userService.updateUser(updatedUser);
      response = new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Updates the active status of a user.
   *
   * @param id The ID of the user.
   * @param active The new active status.
   * @return Return responseEntity with the operation result.
   */
  @PutMapping("/{id}/active")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an user status",
          description = "Updates a user status by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The user is updated."),
          @ApiResponse(responseCode = "404", description = "User is found.", content = @Content)
  })
  public ResponseEntity<User> updateUserActive(@PathVariable int id, @RequestParam boolean active) {
    ResponseEntity<User> response;
    Optional<User> user = userService.getUserById(id);
    if(user.isPresent()){
      logger.info("User status updated.");
      user.get().setActive(active);
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      logger.warn("User not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
