package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("trip")
public class TripController {

  @Autowired
  private TripService tripService;

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

}
