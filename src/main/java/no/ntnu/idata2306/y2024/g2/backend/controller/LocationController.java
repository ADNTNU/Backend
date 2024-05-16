package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.LocationService;
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
@RequestMapping("location")
@Tag(name = "Location API")
public class LocationController {

  @Autowired
  private LocationService locationService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);


  /**
   * Return all Locations
   *
   * @return Return all Locations.
   */
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

  @GetMapping("/{id}")
  public ResponseEntity<Location> getOne(@PathVariable Integer id) {
    ResponseEntity<Location> response;
    Optional<Location> location = locationService.getLocation(id);
    if (location.isPresent()) {
      response = new ResponseEntity<>(location.get(), HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }


  @PostMapping
  //@PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Location",
          description = "Creates a new location. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @JsonView(Views.NoId.class)
  public ResponseEntity<String> addOne(@RequestBody Location location) {
    ResponseEntity<String> response;
    locationService.addLocation(location);
    response = new ResponseEntity<>("", HttpStatus.OK);
    return response;
  }

  @PutMapping("/{id}")
  //@PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Location",
          description = "Updates a location by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Location> updateLocation(@PathVariable Integer id, @RequestBody Location updatedLocation) {
    Optional<Location> existingLocation = locationService.getLocation(id);
    if (existingLocation.isPresent()) {
      updatedLocation.setId(existingLocation.get().getId());
      locationService.updateLocation(updatedLocation);
      return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
    Optional<Location> existingLocation = locationService.getLocation(id);
    if (existingLocation.isPresent()) {
      locationService.deleteLocationById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
