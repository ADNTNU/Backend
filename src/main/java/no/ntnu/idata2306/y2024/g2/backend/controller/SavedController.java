package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.services.SavedService;
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
 * Represents a rest controller for Saved entities.
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
  private static final Logger logger = LoggerFactory.getLogger(SavedController.class);

  /**
   * Constructs an instance of SavedController with necessary dependency.
   *
   * @param savedService The Service handling saved operations.
   */
  @Autowired
  public SavedController(SavedService savedService){
    this.savedService = savedService;
  }

  /**
   * Retrieves all saved items from the database.
   *
   * @return Return a ResponseEntity containing a list of saved items or a status indicating no content.
   */
  @GetMapping
  @Operation(summary = "Retrieve all saved items", description = "Fetches a list of all saved items from the database.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of saved items returned successfully", content = @Content),
          @ApiResponse(responseCode = "204", description = "No saved items available", content = @Content)
  })
  public ResponseEntity<List<Saved>> getAll(){
    ResponseEntity<List<Saved>> response;
    List<Saved> saves = new ArrayList<>(savedService.getAllSaves());
    if(saves.isEmpty()){
      logger.warn("No saves found.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      logger.info("Retuning all saves.");
      response = new ResponseEntity<>(saves, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single Saved based on id.
   *
   * @param id The id of the saved
   * @return Return a single saved based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single saved.", description = "Get a single JSON object with the saved.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Saved return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Saved are available, not found.", content = @Content)
  })
  public ResponseEntity<Saved> getOne(@PathVariable Integer id) {
    ResponseEntity<Saved> response;
    Optional<Saved> saved = savedService.getSaved(id);
    if (saved.isPresent()) {
      logger.info("Returning a single Saved.");
      response = new ResponseEntity<>(saved.get(), HttpStatus.OK);
    } else {
      logger.warn("No Saved with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a new saved item to the database.
   *
   * @param saved The saved item to be added.
   * @return a ResponseEntity with the status of the operation.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Add a new saved item", description = "Adds a new item to the saved items of the user." +
          "Requires ROLE_USER authority.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Saved item added successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content)
  })
  public ResponseEntity<Saved> addOne(@RequestBody Saved saved) {
    ResponseEntity<Saved> response;
    if(saved.isValid()){
      logger.info("Added new Saved.");
      savedService.addSaved(saved);
      response = new ResponseEntity<>(saved, HttpStatus.OK);
    }else{
      logger.warn("Saved is invalid.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing Saved
   *
   * @param id The id of the saved to be updated
   * @param updatedSaved The saved object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Update an existing Saved",
          description = "Updates a saved by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Saved> updateSaved(@PathVariable Integer id, @RequestBody Saved updatedSaved) {
    ResponseEntity<Saved> response;
    Optional<Saved> existingSaved = savedService.getSaved(id);

    if (existingSaved.isEmpty()) {
      logger.warn("Cannot find the saved based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!updatedSaved.isValid()) {
      logger.warn("Saved is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else {
      logger.info("Updating a single Saved.");
      updatedSaved.setId(existingSaved.get().getId());
      savedService.updateSaved(updatedSaved);
      response = new ResponseEntity<>(updatedSaved, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing Saved.
   *
   * @param id The id of the Saved to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Delete a Saved",
          description = "Deletes a Saved by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Optional<Saved>> deleteSaved(@PathVariable Integer id) {
    ResponseEntity<Optional<Saved>> response;
    Optional<Saved> existingSaved = savedService.getSaved(id);
    if (existingSaved.isPresent()) {
      logger.info("Deleting Saved");
      savedService.deleteSavesById(id);
      response = new ResponseEntity<>(existingSaved, HttpStatus.OK);
    } else {
      logger.warn("Saved not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }


}
