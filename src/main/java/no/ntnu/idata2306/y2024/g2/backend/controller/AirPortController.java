package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("airport")
public class AirPortController {

  @Autowired
  private AirPortService airPortService;

  @GetMapping
  public ResponseEntity<List<Airport>> getAll(){
    ResponseEntity<List<Airport>> response;
    List<Airport> airports = new ArrayList<>();
    airPortService.getAllAirPorts().forEach(airports::add);
    if(airports.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(airports, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Airport airport) {
    ResponseEntity<String> response;
    if(airport != null){
      airPortService.addAirPort(airport);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
