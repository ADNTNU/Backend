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
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Flight;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.FlightService;
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
@CrossOrigin
@RequestMapping("flight")
@Tag(name = "Flight API")
public class FlightController {

  private final FlightService flightService;
  private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

  @Autowired
  public FlightController(FlightService flightService){
    this.flightService = flightService;
  }

  @GetMapping
  public ResponseEntity<List<Flight>> getAll(){
    ResponseEntity<List<Flight>> response;
    List<Flight> flights = new ArrayList<>();
    flightService.getAllFlights().forEach(flights::add);
    if(flights.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(flights, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single location based on id.
   *
   * @param id The id of the location
   * @return Return a single location based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Flight.", description = "Get a single JSON object with the Flight.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Flight return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Flight are available, not found.", content = @Content)
  })
  public ResponseEntity<Flight> getOne(@PathVariable Integer id) {
    ResponseEntity<Flight> response;
    Optional<Flight> flight = flightService.getFlight(id);
    if (flight.isPresent()) {
      logger.info("Returning a single Flight.");
      response = new ResponseEntity<>(flight.get(), HttpStatus.OK);
    } else {
      logger.warn("No Flight with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Flight",
          description = "Creates a new Flight. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Flight> addOne(@RequestBody Flight flight) {
    ResponseEntity<Flight> response;
    if(flight.isValid()){
      flightService.addFlight(flight);
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing location
   *
   * @param id The id of the location to be updated
   * @param updatedLocation The location object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Location",
          description = "Updates a location by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Flight> updateLocation(@PathVariable Integer id, @RequestBody Flight flight) {
    ResponseEntity<Flight> response;
    Optional<Flight> existingFlight = flightService.getFlight(id);

    if (existingFlight.isEmpty()) {
      logger.warn("Cannot find the flight based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!flight.isValid()) {
      logger.warn("Flight is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else {
      logger.info("Updating a single Flight.");
      flight.setId(existingFlight.get().getId());
      flightService.updateFlight(flight);
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing location.
   *
   * @param id The id of the location to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Flight",
          description = "Deletes a Flight by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Optional<Flight>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<Flight>> response;
    Optional<Flight> flight = flightService.getFlight(id);

    if (flight.isPresent()) {
      flightService.deleteFlightById(id);
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
