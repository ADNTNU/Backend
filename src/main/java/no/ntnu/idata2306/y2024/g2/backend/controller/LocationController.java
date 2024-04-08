package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("location")
public class LocationController {

  @Autowired
  private LocationService locationService;

  @GetMapping
  public ResponseEntity<List<Location>> getAll(){
    ResponseEntity<List<Location>> response;
    List<Location> locations = new ArrayList<>();
    locationService.getAllLocations().forEach(locations::add);
    if(locations.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else {
      response = new ResponseEntity<>(locations, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Location location) {
    ResponseEntity<String> response;
    locationService.addLocation(location);
    response = new ResponseEntity<>("", HttpStatus.OK);
    return response;
  }

}
