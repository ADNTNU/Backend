package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Flight;
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

/**
 * Represents a rest controller for locations entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("flight")
@Tag(name = "Flight API")
public class FlightController {

  private final FlightService flightService;
  private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

  /**
   * Constructs an instance of FlightController with necessary dependency.
   *
   * @param flightService The Service handling flight operations.
   */
  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }


  /**
   * Retrieves all flights from the database.
   *
   * @return Return a ResponseEntity containing a list of flights or an empty list if no flights exist.
   */
  @GetMapping
  @Operation(summary = "Get all Flights", description = "Retrieve all flights available in the database.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all flights."),
      @ApiResponse(responseCode = "204", description = "No flights are available.")
  })
  public ResponseEntity<List<Flight>> getAll() {
    ResponseEntity<List<Flight>> response;
    List<Flight> flights = new ArrayList<>(flightService.getAllFlights());
    if (flights.isEmpty()) {
      logger.warn("There is no flight in the list");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all flights.");
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

  /**
   * Adds a new flight to the database.
   *
   * @param flight The Flight entity to add.
   * @return Return a ResponseEntity with the created Flight if successful, or a Bad Request status if not valid.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Flight",
      description = "Creates a new Flight. Requires ROLE_USER authority. Only need to provide the" +
          "id for the departure, arrival and airport id.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Flight successfully created."),
      @ApiResponse(responseCode = "400", description = "Invalid flight information provided.", content = @Content)
  })
  public ResponseEntity<Flight> addOne(@RequestBody Flight flight) {
    ResponseEntity<Flight> response;
    if (flight.isValid()) {
      logger.info("Added new flight.");
      flightService.addFlight(flight);
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    } else {
      logger.warn("Flight is invalid.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing location
   *
   * @param id     The id of the location to be updated
   * @param flight The location object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Location",
      description = "Updates a location by its ID. Requires ROLE_USER authority. Only need to provide the" +
          "id for the departure, arrival and airport id.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Flight successfully updated."),
      @ApiResponse(responseCode = "404", description = "Flight not found.", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid flight data provided.", content = @Content)
  })
  public ResponseEntity<Flight> updateLocation(@PathVariable Integer id, @RequestBody Flight flight) {
    ResponseEntity<Flight> response;
    Optional<Flight> existingFlight = flightService.getFlight(id);

    if (existingFlight.isEmpty()) {
      logger.warn("Cannot find the flight based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (!flight.isValid()) {
      logger.warn("Flight is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Flight successfully deleted."),
      @ApiResponse(responseCode = "404", description = "Flight not found.", content = @Content)
  })
  public ResponseEntity<Optional<Flight>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<Flight>> response;
    Optional<Flight> flight = flightService.getFlight(id);
    if (flight.isPresent()) {
      logger.info("Deleting flight");
      flightService.deleteFlightById(id);
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    } else {
      logger.warn("Flight not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
