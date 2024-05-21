package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.RoleUserDTO;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.RoleService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Tag(name = "User API")
@CrossOrigin
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public UserController(UserService userService, RoleService roleService){
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping
  @JsonView(Views.hidePassword.class)
  public ResponseEntity<List<User>> getAll() {
    ResponseEntity<List<User>> response;
    List<User> users = new ArrayList<>();
    userService.getAllUsers().forEach(users::add);
    if (users.isEmpty()) {
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      response = new ResponseEntity<>(users, HttpStatus.OK);
    }
    return response;
  }

  @GetMapping("/{id}")
  @JsonView(Views.hidePassword.class)
  public ResponseEntity<User> getOne(@PathVariable Integer id) {
    System.out.println("Get user with id: " + id);
    ResponseEntity<User> response;
    Optional<User> user = userService.getUserById(id);
    response = user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    return response;
  }

  /**@PostMapping
  @JsonView(Views.IdOnly.class)
  public ResponseEntity<String> addOne(@RequestBody User user) {
    logger.warn("Add user: " + user.getFirstName() + " " + user.getLastName());
    ResponseEntity<String> response;
    if(user != null){
      userService.addUser(user);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }*/


  @PostMapping("/addRole")
  public ResponseEntity<RoleUserDTO> addRole(@RequestBody RoleUserDTO roleUserDTO){
    ResponseEntity<RoleUserDTO> response;
    Optional<User> user = userService.getUserById(roleUserDTO.getUser().getId());
    Optional<Role> role = roleService.getRoleById(roleUserDTO.getRole().getId());

    if(user.isPresent() && role.isPresent()){
      user.get().addRole(role.get());
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @PostMapping("/removeRole")
  public ResponseEntity<RoleUserDTO> removeRole(@RequestBody RoleUserDTO roleUserDTO){
    ResponseEntity<RoleUserDTO> response;
    Optional<User> user = userService.getUserById(roleUserDTO.getUser().getId());
    Optional<Role> role = roleService.getRoleById(roleUserDTO.getRole().getId());

    if(user.isPresent() && role.isPresent()){
      user.get().removeRole(role.get());
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }


  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
    Optional<User> existingUser = userService.getUserById(id);
    if (existingUser.isPresent()) {
      userService.deleteUserById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser){
    Optional<User> existingUser = userService.getUserById(id);
    if (existingUser.isPresent()) {
      updatedUser.setId(existingUser.get().getId());
      userService.updateUser(updatedUser);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}/active")
  public ResponseEntity<String> updateUserActive(@PathVariable int id, @RequestParam boolean active) {
    ResponseEntity<String> response;
    Optional<User> user = userService.getUserById(id);
    if(user.isPresent()){
      user.get().setActive(active);
      userService.updateUser(user.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
