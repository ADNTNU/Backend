package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.*;
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

@RestController
@RequestMapping("trip")
@Tag(name = "Trip API")
public class TripController {

  @Autowired
  private TripService tripService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping
  public ResponseEntity<List<Trip>> getAll(){
    ResponseEntity<List<Trip>> response;
    List<Trip> trips = new ArrayList<>();
    tripService.getAllTrips().forEach(trips::add);
    if(trips.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
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
   * Tell them about how to do that and that.
   * @param trip
   * @return
   */
  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Trip trip) {
    ResponseEntity<String> response;
    if(trip != null){
      tripService.addTrip(trip);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

  @DeleteMapping("{id}")
  @Operation(summary = "Delete Airline by id", description = "Delete an airline based on the unique id provided")
  public ResponseEntity<String> deleteTripById(@PathVariable int id){
    ResponseEntity<String> response;
    try{
      tripService.deleteTripById(id);
      logger.info("Deleting trip with ID: " + id);
      response = new ResponseEntity<>("Deleted", HttpStatus.OK);
    } catch (EntityNotFoundException entityNotFoundException){
      logger.warn("No trip with the ID: " + id);
      response = new ResponseEntity<>("No airline with that Id", HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  @PutMapping("/{id}/active")
  public ResponseEntity<String> updateTripActive(@PathVariable int id, @RequestParam boolean active) {
    ResponseEntity<String> response;
    Optional<Trip> trip = tripService.getTrip(id);
    if(trip.isPresent()){
      trip.get().setActive(active);
      tripService.updateTrip(trip.get());
      response = new ResponseEntity<>(HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
