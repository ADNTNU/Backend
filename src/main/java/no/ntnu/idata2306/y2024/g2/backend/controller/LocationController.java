package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.PopularDestination;
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

/**
 * Represents a rest controller for locations entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("location")
@Tag(name = "Location API")
public class LocationController {

  private final LocationService locationService;
  private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

  /**
   * Constructs an instance of LocationController with necessary dependency.
   *
   * @param locationService The Service handling location operations.
   */
  @Autowired
  public LocationController(LocationService locationService) {
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
  public ResponseEntity<List<Location>> getAll() {
    ResponseEntity<List<Location>> response;
    List<Location> locations = new ArrayList<>(locationService.getAllLocations());
    if (locations.isEmpty()) {
      logger.warn("There is no location to return, list is empty.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all Locations.");
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
      logger.info("Returning a single Locations.");
      response = new ResponseEntity<>(location.get(), HttpStatus.OK);
    } else {
      logger.warn("No Location with that id.");
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The Location return in the response body."),
      @ApiResponse(responseCode = "400", description = "No Location are available, not found.", content = @Content)
  })
  public ResponseEntity<Location> addOne(@RequestBody Location location) {
    ResponseEntity<Location> response;
    if (location.isValid()) {
      logger.info("Adding a single Locations.");
      locationService.addLocation(location);
      response = new ResponseEntity<>(location, HttpStatus.OK);
    } else {
      logger.warn("Location is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing location
   *
   * @param id              The id of the location to be updated
   * @param updatedLocation The location object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Location",
      description = "Updates a location by its ID. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The location was updated successfully", content = @Content),
      @ApiResponse(responseCode = "404", description = "No location found with the specified ID", content = @Content),
      @ApiResponse(responseCode = "400", description = "Location trip data provided", content = @Content)
  })
  public ResponseEntity<Location> updateLocation(@PathVariable Integer id, @RequestBody Location updatedLocation) {
    ResponseEntity<Location> response;
    Optional<Location> existingLocation = locationService.getLocation(id);
    if (existingLocation.isEmpty()) {
      logger.warn("Cannot find the location based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (!updatedLocation.isValid()) {
      logger.warn("Location is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Updating a single Locations.");
      updatedLocation.setId(existingLocation.get().getId());
      locationService.updateLocation(updatedLocation);
      response = new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
    return response;
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The location was updated successfully", content = @Content),
      @ApiResponse(responseCode = "404", description = "No location with that id.", content = @Content)
  })
  public ResponseEntity<Optional<Location>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<Location>> response;
    Optional<Location> existingLocation = locationService.getLocation(id);
    if (existingLocation.isPresent()) {
      logger.info("Location deleted");
      locationService.deleteLocationById(id);
      response = new ResponseEntity<>(existingLocation, HttpStatus.OK);
    } else {
      logger.warn("Cant find location with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @GetMapping("/popularDestinations")
  public ResponseEntity<?> getPopularDestinations(@RequestParam(required = false) Integer fromLocationId,
                                                  @RequestParam(required = false, name = "l") Integer limit
  ) {
    if (limit == null) {
      limit = 5;
    }
    Location fromLocation = fromLocationId != null ? locationService.getLocation(fromLocationId).orElse(null) : null;
    List<PopularDestination> locations = locationService.getPopularDestinations(fromLocation, true, limit);

    if (locations.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No popular destinations found.");
    }
    return ResponseEntity.ok(locations);
  }

}
