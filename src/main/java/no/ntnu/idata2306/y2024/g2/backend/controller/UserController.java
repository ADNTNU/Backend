package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<User> getOne(@PathVariable Integer id) {
    System.out.println("Get user with id: " + id);
    ResponseEntity<User> response;
    Optional<User> user = userService.getUserById(id);
    response = user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    return response;
  }

  @PostMapping
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
  }


}
