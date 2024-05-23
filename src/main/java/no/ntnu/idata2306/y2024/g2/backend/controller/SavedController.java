package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.UserSaveDTO;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AccessUserService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.SavedService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.TripService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Represents a rest controller for Saved entities.
 * Has user Authentication, uses AccessUserService to check if a user
 * should have access to that resource or not. (One user cannot
 * go and change data for another user)
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 21.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("saved")
@Tag(name = "User-Saves API")
public class SavedController {

  private final SavedService savedService;
  private final UserService userService;
  private final TripService tripService;
  private final AccessUserService accessUserService;
  private static final Logger logger = LoggerFactory.getLogger(SavedController.class);

  /**
   * Constructs an instance of SavedController with necessary dependency.
   *
   * @param savedService The Service handling saved operations.
   * @param userService The Service handling user operations.
   */
  @Autowired
  public SavedController(SavedService savedService, UserService userService, TripService tripService, AccessUserService accessUserService){
    this.savedService = savedService;
    this.userService = userService;
    this.tripService = tripService;
    this.accessUserService = accessUserService;
  }

  /**
   * Return a list of saves from user email.
   *
   * @return Return a list with saves.
   */
  @GetMapping()
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Get a list of saves.", description = "Get a list of saves for the provided users email." +
          "Requires ROLE_USER authority and user authentication.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Saves return in the response body."),
          @ApiResponse(responseCode = "400", description = "No Saves are available, not found.", content = @Content),
          @ApiResponse(responseCode = "403", description = "Not authenticated.", content = @Content)
  })
  public ResponseEntity<List<Saved>> getSavesFromEmail() {
    User sessionUser = accessUserService.getSessionUser();
    ResponseEntity<List<Saved>> response;
    if(sessionUser != null){
        List<Saved> saves = savedService.getAllSavesWithEmail(sessionUser.getEmail());
        response = new ResponseEntity<>(saves, HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Adds a new save to the database.
   *
   * @param userSaveDTO The email and trip id to be saved.
   * @return Return a ResponseEntity with the status of the operation.
   */
  @PostMapping()
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Add a new saved item linked to a users email", description = "Adds a new item to the saved and it is linked to the users" +
          "by the users email address. It is also authenticated towards it." +
          "Requires ROLE_USER authority and user authentication.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Saved item added successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content),
          @ApiResponse(responseCode = "403", description = "Not authenticated.", content = @Content)
  })
  public ResponseEntity<?> addNewSavedFromEmail(@RequestBody UserSaveDTO userSaveDTO) {
    User sessionUser = accessUserService.getSessionUser();
    ResponseEntity<?> response;
    Optional<Trip> trip = tripService.getTrip(userSaveDTO.getTripId());

    if(trip.isPresent()){
      if(sessionUser != null) {
        logger.info("Added new Saved.");
        Saved saved = new Saved(sessionUser, trip.get(), LocalDateTime.now());
        savedService.addSaved(saved);
        response = new ResponseEntity<>(HttpStatus.OK);
      }else {
        response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }
    }else{
      logger.warn("Invalid data provided.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  /**
   * Delete an existing Saved based on email and id.
   *
   * @param id The id of the Saved to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("{id}")
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Delete a Saved",
      description = "Deletes a Saved by its ID. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Saved item added successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "No finds with that id.", content = @Content),
          @ApiResponse(responseCode = "403", description = "Not authorized to do that", content = @Content),
          @ApiResponse(responseCode = "401", description = "Not authenticated.", content = @Content)
  })
  public ResponseEntity<Optional<Saved>> deleteSaved(@PathVariable Integer id) {
    ResponseEntity<Optional<Saved>> response;
    User sessionUser = accessUserService.getSessionUser();
    if(sessionUser != null){
      Optional<Saved> existingSaved = savedService.getSaved(id);
      if (existingSaved.isPresent()) {
        Saved saved = existingSaved.get();
        if(sessionUser.getEmail().equals(saved.getUser().getEmail())){
          logger.info("Deleting Saved");
          savedService.deleteSavesById(id);
          response = new ResponseEntity<>(existingSaved, HttpStatus.OK);
        }else{
          logger.warn("User not permitted to change.");
          response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
      } else {
        logger.warn("Saved not found.");
        response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }else{
      logger.warn("User not authenticated.");
      response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return response;
  }

}
