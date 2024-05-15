package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirlineService;
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

@RestController
@RequestMapping("airline")
@Tag(name = "Airline API")
public class AirLineController {

  @Autowired
  private AirlineService airlineService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping
  @Operation(summary = "Get all Airlines", description = "Get an Json list of all Airlines.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "No Airlines are available, so no content.", content = @Content),
          @ApiResponse(responseCode = "200", description = "The Airlines returned in the response body")
  })
  public ResponseEntity<List<Airline>> getAll(){
    ResponseEntity<List<Airline>> response;
    List<Airline> airlines = new ArrayList<>();
    airlineService.getAllAirlines().forEach(airlines::add);
    if(airlines.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(airlines, HttpStatus.OK);
    }
    return response;
  }

  @GetMapping("{id}")
  @Operation(summary = "Get Airline by id", description = "Uses the unique id to find the desired Airline")
  public ResponseEntity<Optional<Airline>> getAirlineById(@PathVariable int id){
    ResponseEntity<Optional<Airline>> response;
    try{
      Optional<Airline> airline = airlineService.getAirline(id);
      logger.info("Returning Airline with ID: " + id);
      response = new ResponseEntity<>(airline, HttpStatus.OK);
    }catch (EntityNotFoundException entityNotFoundException){
      logger.warn("No airline with the ID: " + id);
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  @PostMapping
  @Operation(summary = "Add an Airline", description = "Send Json data to be saved to the database",
          security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Airline added successfully"),
          @ApiResponse(responseCode = "404", description = "Airline not added successfully",
                  content = @Content)
  })
  public ResponseEntity<String> addOne(@RequestBody Airline airline) {
    ResponseEntity<String> response;
    if(airline.isValid()){
      airlineService.addAirline(airline);
      response = new ResponseEntity<>("Airline added successfully.", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>("Airline data is missing or incorrect.",HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Delete Airline by id", description = "Delete an airline based on the unique id provided")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> deleteAirlineById(@PathVariable int id){
    ResponseEntity<String> response;
    try{
      airlineService.deleteAirlineById(id);
      logger.info("Deleting Airline with ID: " + id);
      response = new ResponseEntity<>("Deleted", HttpStatus.OK);
    } catch (EntityNotFoundException entityNotFoundException){
      logger.warn("No airline with the ID: " + id);
      response = new ResponseEntity<>("No airline with that Id", HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airline> updateUser(@PathVariable Integer id, @RequestBody Airline updatedAirline){
    Optional<Airline> existingAirline = airlineService.getAirline(id);
    if (existingAirline.isPresent()) {
      updatedAirline.setId(existingAirline.get().getId());
      airlineService.updateAirline(updatedAirline);
      return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
