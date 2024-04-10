package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("airline")
public class AirLineController {

  @Autowired
  private AirlineService airlineService;

  @GetMapping
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

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Airline airline) {
    ResponseEntity<String> response;
    if(airline != null){
      airlineService.addAirline(airline);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
