package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirlineService;
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
 * Controller for managing airline data. It provides endpoints to retrieve, add, delete, and update airline information.
 * Secured endpoints require admin privileges for modification actions.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("airline")
@Tag(name = "Airline API")
public class AirlineController {

  private final AirlineService airlineService;
  private static final Logger logger = LoggerFactory.getLogger(AirlineController.class);

  /**
   * Constructs an AirlineController with the required AirlineService.
   *
   * @param airlineService The service used to manage airline data.
   */
  @Autowired
  public AirlineController(AirlineService airlineService){
    this.airlineService = airlineService;
  }

  /**
   * Retrieves all airlines from the database.
   *
   * @return Return a ResponseEntity containing a list of airlines or an empty list if no airlines exist.
   */
  @GetMapping
  @Operation(summary = "Get all Airlines", description = "Get an Json list of all Airlines.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "No Airlines are available, so no content.", content = @Content),
          @ApiResponse(responseCode = "200", description = "The Airlines returned in the response body")
  })
  public ResponseEntity<List<Airline>> getAll(){
    ResponseEntity<List<Airline>> response;
    List<Airline> airlines = new ArrayList<>(airlineService.getAllAirlines());
    if(airlines.isEmpty()){
      logger.warn("There is no Airline to return, list is empty.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      logger.info("Returning all Airlines.");
      response = new ResponseEntity<>(airlines, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Retrieves a specific airline by its ID.
   *
   * @param id The ID of the airline to retrieve.
   * @return Return a ResponseEntity containing the requested airline or not found status if it does not exist.
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get Airline by id", description = "Uses the unique id to find the desired Airline")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Airline return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Airline are available, not found.", content = @Content)
  })
  public ResponseEntity<Airline> getAirlineById(@PathVariable int id){
    ResponseEntity<Airline> response;
    Optional<Airline> airline = airlineService.getAirline(id);
    if (airline.isPresent()){
      logger.info("Returning a single Airline");
      response = new ResponseEntity<>(airline.get(), HttpStatus.OK);
    }else {
      logger.warn("No Airline with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a new airline to the database.
   *
   * @param airline The Airline to be added.
   * @return Return a ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add an Airline", description = "Send Json data about the airline to be saved to the database",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Airline added successfully"),
          @ApiResponse(responseCode = "400", description = "Airline not added",
                  content = @Content)
  })
  public ResponseEntity<Airline> addOne(@RequestBody Airline airline) {
    ResponseEntity<Airline> response;
    if(airline.isValid()){
      logger.info("Adding a single Airline.");
      airlineService.addAirline(airline);
      response = new ResponseEntity<>(airline, HttpStatus.OK);
    }else{
      logger.warn("Airline is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Deletes an airline by its ID.
   *
   * @param id The ID of the airline to delete.
   * @return Return a ResponseEntity indicating the result of the delete operation.
   */
  @DeleteMapping("{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete Airline by id", description = "Delete an airline based on the unique id provided",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Airline has been deleted."),
          @ApiResponse(responseCode = "400", description = "No Airline are available, not found.", content = @Content)
  })
  public ResponseEntity<Optional<Airline>> deleteAirlineById(@PathVariable int id){
    ResponseEntity<Optional<Airline>> response;
    Optional<Airline> airline = airlineService.getAirline(id);
    if(airline.isPresent()){
      logger.info("Deleting Airline.");
      airlineService.deleteAirlineById(id);
      response = new ResponseEntity<>(airline, HttpStatus.OK);
    }else{
      logger.warn("Cannot find Airline with that id.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Updates an existing airline by its ID.
   *
   * @param id The ID of the airline to update.
   * @param updatedAirline The updated airline data.
   * @return Return a ResponseEntity containing the updated airline or a not found status.
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update Airline by id", description = "Update an airline based on the unique id provided",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Airline return in the response body."),
          @ApiResponse(responseCode = "400", description = "The Airline is invalid.", content = @Content),
          @ApiResponse(responseCode = "404", description = "No Airline are available, not found.", content = @Content)
  })
  public ResponseEntity<Airline> updateAirline(@PathVariable Integer id, @RequestBody Airline updatedAirline){
    ResponseEntity<Airline> response;
    Optional<Airline> existingAirline = airlineService.getAirline(id);

    if (existingAirline.isEmpty()) {
      logger.warn("Cannot find the Airline based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    } else if(!updatedAirline.isValid()){
      logger.warn("Airline is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else{
      logger.info("Updating a single Airline.");
      updatedAirline.setId(existingAirline.get().getId());
      airlineService.updateAirline(updatedAirline);
      return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }
    return response;
  }

}
