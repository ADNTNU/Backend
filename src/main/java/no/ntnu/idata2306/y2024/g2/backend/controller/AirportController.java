package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirportService;
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
public class AirportController {

  private AirportService airportService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public AirportController(AirportService airportService){
    this.airportService = airportService;
  }


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
    airportService.getAllAirports().forEach(airports::add);
    if(airports.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
      logger.warn("There is no Airlines in the database.");
    }else{
      response = new ResponseEntity<>(airports, HttpStatus.OK);
      logger.info("Return all airports. Amount : " + airports.size());
    }
    return response;
  }

  /**
   * Return a single Airport based on id.
   *
   * @param id The id of the location
   * @return Return a single location based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Airport.", description = "Get a single JSON object with the Airport.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Location return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Location are available, not found.", content = @Content)
  })
  public ResponseEntity<Airport> getOne(@PathVariable Integer id) {
    ResponseEntity<Airport> response;
    Optional<Airport> airport = airportService.getAirport(id);
    if (airport.isPresent()) {
      logger.info("Returning a single Airport.");
      response = new ResponseEntity<>(airport.get(), HttpStatus.OK);
    } else {
      logger.warn("No Airport with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Location",
          description = "Creates a new Airport. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Airport> addOne(@RequestBody Airport airport) {
    ResponseEntity<Airport> response;
    if(airport.isValid()){
      logger.info("Adding a single Airport");
      airportService.addAirport(airport);
      response = new ResponseEntity<>(airport, HttpStatus.OK);
    }else{
      logger.warn("The Airline is not valid!");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Airport",
          description = "Updates a Airport by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Airport> updateAirport(@PathVariable Integer id, @RequestBody Airport airport){
    ResponseEntity<Airport> response;
    Optional<Airport> existingAirport = airportService.getAirport(id);

    if (!existingAirport.isPresent()) {
      logger.warn("Cannot find the Airport based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!airport.isValid()) {
      logger.warn("Airport is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else {
      logger.info("Updating a single Airport.");
      airport.setId(existingAirport.get().getId());
      airportService.updateAirport(airport);
      response = new ResponseEntity<>(airport, HttpStatus.OK);
    }
    return response;
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Optional<Airport>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<Airport>> response;
    Optional<Airport> existingAirport = airportService.getAirport(id);

    if (existingAirport.isPresent()) {
      logger.info("Airport deleted");
      airportService.deleteAirportById(id);
      response = new ResponseEntity<>(existingAirport, HttpStatus.OK);
    } else {
      logger.warn("Cannot delete airport that dont exist.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

}
