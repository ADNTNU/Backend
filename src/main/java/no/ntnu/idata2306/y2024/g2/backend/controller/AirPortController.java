package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirPortService;
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
 * Represents a rest controller for Airport entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 17.10.2024
 */
@RestController
@CrossOrigin
@RequestMapping("airport")
@Tag(name = "Airport API")
public class AirPortController {

  @Autowired
  private AirPortService airPortService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Return all airports.
   *
   * @return Return all airports.
   */
  @GetMapping
  @Operation(summary = "Get all Airports.", description = "Get an JSON object with a list of all the airports.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Airport list return in the response body."),
          @ApiResponse(responseCode = "204", description = "No Airports are available, none exist yet.", content = @Content)
  })
  public ResponseEntity<List<Airport>> getAll(){
    ResponseEntity<List<Airport>> response;
    List<Airport> airports = new ArrayList<>();
    airPortService.getAllAirPorts().forEach(airports::add);
    if(airports.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
      logger.warn("There is no Airlines in the database.");
    }else{
      response = new ResponseEntity<>(airports, HttpStatus.OK);
      logger.info("Return all airports. Amount : " + airports.size());
    }
    return response;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Location",
          description = "Creates a new Airport. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @JsonView(Views.IdOnly.class)
  public ResponseEntity<String> addOne(@RequestBody Airport airport) {
    ResponseEntity<String> response;
    if(airport.isValid()){
      airPortService.addAirPort(airport);
      response = new ResponseEntity<>("", HttpStatus.OK);
      logger.info("New Airport added with the ID: " + airport.getId());
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      logger.warn("The Airline is not valid!");
    }
    return response;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airport> updateAirport(@PathVariable Integer id, @RequestBody Airport airport){
    Optional<Airport> existingAirport = airPortService.getAirPortById(id);
    if (existingAirport.isPresent()) {
      airport.setId(existingAirport.get().getId());
      airPortService.updateAirPort(airport);
      return new ResponseEntity<>(airport, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
    Optional<Airport> existingAirport = airPortService.getAirPortById(id);
    if (existingAirport.isPresent()) {
      airPortService.deleteAirPortById(id);
      logger.info("New Airport with ID: " + id + " Has been removed.");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.warn("Cannot delete airport that dont exist.");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
