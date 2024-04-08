package no.ntnu.idata2306.y2024.g2.backend;

import io.swagger.v3.oas.annotations.Operation;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirlineRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.SavedRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("flights")
public class ApiController {

  @Autowired
  private AirlineRepository airlineRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SavedRepository savedRepository;

  @GetMapping()
  @Operation(summary = "Returns an string of Hei, used for initial testing.")
  public List<Airline> test(){
    List<Airline> airlines = new ArrayList<>();
    airlineRepository.findAll().forEach(airlines::add);
    return airlines;
  }

  @GetMapping("/user")
  @Operation(summary = "Returns an string of Hei, used for initial testing.")
  public List<User> getUsers(){
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  @PostMapping
  public void addAirline(@RequestBody Airline airline) {
    airlineRepository.save(airline);}

  @PostMapping("/user")
  public void addAirline(@RequestBody User user) {userRepository.save(user);}

  @PostMapping("/save/{id}")
  public void addAirline2(@RequestBody Saved saved, @PathVariable int userId) {

    //get user by id
    //saved.setUser();
    savedRepository.save(saved);
  }

}
