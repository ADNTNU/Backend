package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("location")
@Tag(name = "Location API")
public class LocationController {

  @Autowired
  private LocationService locationService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  @GetMapping
  @Operation(summary = "Get all Locations.", description = "Get an JSON list of all Locations.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Location return in the response body."),
          @ApiResponse(responseCode = "204", description = "No Location are available, none exist.", content = @Content)
  })
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
