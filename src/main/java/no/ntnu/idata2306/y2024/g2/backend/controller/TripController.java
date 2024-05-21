package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.services.TripService;
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
 * Represents a rest controller for trips entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("trip")
@Tag(name = "Trip API")
public class TripController {

  private final TripService tripService;
  private static final Logger logger = LoggerFactory.getLogger(TripController.class);

  @Autowired
  public TripController(TripService tripService){
    this.tripService = tripService;
  }

  /**
   * Return all trips
   *
   * @return Return all Trips.
   */
  @GetMapping
  @Operation(summary = "Get all Trips.", description = "Get an JSON list of all Trip.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Trips return in the response body."),
          @ApiResponse(responseCode = "204", description = "No Trips are available, none exist.", content = @Content)
  })
  public ResponseEntity<List<Trip>> getAll(){
    ResponseEntity<List<Trip>> response;
    List<Trip> trips = new ArrayList<>(tripService.getAllTrips());
    if(trips.isEmpty()){
      logger.warn("There is no trips.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      logger.info("Return all trips.");
      response = new ResponseEntity<>(trips, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single trip based on id.
   *
   * @param id The id of the trip
   * @return Return a single trip based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Trip.", description = "Get a single JSON object with the trip.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Trip return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Trip are available, not found.", content = @Content)
  })
  public ResponseEntity<Trip> getOne(@PathVariable Integer id) {
    ResponseEntity<Trip> response;
    Optional<Trip> trip = tripService.getTrip(id);
    if (trip.isPresent()) {
      logger.info("Returning a single Trip.");
      response = new ResponseEntity<>(trip.get(), HttpStatus.OK);
    } else {
      logger.warn("No Trip with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Creates a new trip.
   *
   * @param trip The trip to add.
   * @return Return a response entity with appropriate status.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new trip",
          description = "Creates a new trip. Requires ROLE_USER authority. Can use ID on" +
                  "the flight fields.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Trip return in the response body."),
          @ApiResponse(responseCode = "400", description = "No trip are available, not found.", content = @Content)
  })
  public ResponseEntity<Trip> addOne(@RequestBody Trip trip) {
    ResponseEntity<Trip> response;
    if(trip.isValid()){
      logger.info("Added new trip.");
      tripService.addTrip(trip);
      response = new ResponseEntity<>(trip, HttpStatus.OK);
    }else{
      logger.warn("Trip is invalid");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing Trip
   *
   * @param id The id of the Trip to be updated
   * @param updatedTrip The Trip object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Trip",
          description = "Updates a trip by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses( value = {
          @ApiResponse(responseCode = "200", description = "The trip was updated successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "No trip found with the specified ID", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid trip data provided", content = @Content)
  })
  public ResponseEntity<Trip> updateTrip(@PathVariable Integer id, @RequestBody Trip updatedTrip) {
    ResponseEntity<Trip> response;
    Optional<Trip> existingTrip = tripService.getTrip(id);
    if (existingTrip.isEmpty()) {
      logger.warn("Cannot find the trip based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!updatedTrip.isValid()) {
      logger.warn("Trip is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else {
      logger.info("Updating a single Trip.");
      updatedTrip.setId(existingTrip.get().getId());
      tripService.updateTrip(updatedTrip);
      response = new ResponseEntity<>(updatedTrip, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Deletes a trip by its ID.
   *
   * @param id the ID of the trip to delete.
   * @return Return a response entity with success or error status.
   */
  @DeleteMapping("{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete Trip by id", description = "Delete an trip based on the unique id provided",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The trip was updated successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "No trip found with that id", content = @Content)
  })
  public ResponseEntity<Optional<Trip>> deleteTripById(@PathVariable int id){
    ResponseEntity<Optional<Trip>> response;
    Optional<Trip> trip = tripService.getTrip(id);
    if(trip.isPresent()){
      logger.info("Trip deleted");
      tripService.deleteTripById(id);
      response = new ResponseEntity<>(trip, HttpStatus.OK);
    }else{
      logger.warn("Trip not found");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Updates the active status of a trip.
   *
   * @param id The ID of the trip to update.
   * @param active The new active status.
   * @return Return a response entity with the operation result.
   */
  @PutMapping("/{id}/active")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update trip status by id", description = "Update an trip status based on the unique id provided." +
          "Requires ROLE_ADMIN authority. Use url trip/tripId/active?active=true or false.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses( value = {
          @ApiResponse(responseCode = "200", description = "The trip status was updated successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "Invalid trip data provided", content = @Content)
  })
  public ResponseEntity<String> updateTripActive(@PathVariable int id, @RequestParam boolean active) {
    ResponseEntity<String> response;
    Optional<Trip> trip = tripService.getTrip(id);
    if(trip.isPresent()){
      logger.info("Trip status updated");
      trip.get().setActive(active);
      tripService.updateTrip(trip.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      logger.warn("Trip not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
