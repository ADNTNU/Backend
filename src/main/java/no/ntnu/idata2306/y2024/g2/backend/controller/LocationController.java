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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a rest controller for locations entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 17.10.2024
 */
@RestController
@CrossOrigin
@RequestMapping("location")
@Tag(name = "Location API")
public class LocationController {

  private LocationService locationService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Constructs an instance of LocationController with necessary dependency.
   *
   * @param locationService The Service handling location operations.
   */
  @Autowired
  public LocationController(LocationService locationService){
    this.locationService = locationService;
  }

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

  /**
   * Return a single location based on id.
   *
   * @param id The id of the location
   * @return Return a single location based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Location.", description = "Get a single JSON object with the Location.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Location return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Location are available, not found.", content = @Content)
  })
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

  /**
   * Adds a new location.
   *
   * @param location The location to be added
   * @return Return status code 200 if ok
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Location",
          description = "Creates a new location. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  //@JsonView(Views.NoId.class)
  public ResponseEntity<Location> addOne(@RequestBody Location location) {

    ResponseEntity<Location> response;
    locationService.addLocation(location);
    response = new ResponseEntity<>(location, HttpStatus.OK);
    return response;
  }

  /**
   * Update an existing location
   *
   * @param id The id of the location to be updated
   * @param updatedLocation The location object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
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

  /**
   * Delete an existing location.
   *
   * @param id The id of the location to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Optional<Location>> deleteLocation(@PathVariable Integer id) {
    Optional<Location> existingLocation = locationService.getLocation(id);
    if (existingLocation.isPresent()) {
      locationService.deleteLocationById(id);
      return new ResponseEntity<>(existingLocation, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
