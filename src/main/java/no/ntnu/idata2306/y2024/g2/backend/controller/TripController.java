package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
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

  @PutMapping("/{id}")
  public ResponseEntity<Trip> updateTrip(@PathVariable Integer id, @RequestBody Trip updatedTrip){
    Optional<Trip> existingAirline = tripService.getTrip(id);
    if (existingAirline.isPresent()) {
      updatedTrip.setId(existingAirline.get().getId());
      tripService.updateTrip(updatedTrip);
      return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
