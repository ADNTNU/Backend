package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ExtraFeature;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ExtraFeatureService;
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
 * Controller for managing ExtraFeature entities. Provides CRUD functionalities.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("extraFeature")
@Tag(name = "Extra Feature API")
public class ExtraFeatureController {

  private final ExtraFeatureService extraFeatureService;
  private static final Logger logger = LoggerFactory.getLogger(ExtraFeatureController.class);

  /**
   * Constructs an instance of ExtraFeatureController with necessary dependency.
   *
   * @param extraFeatureService The Service handling ExtraFeature operations.
   */
  @Autowired
  public ExtraFeatureController(ExtraFeatureService extraFeatureService) {
    this.extraFeatureService = extraFeatureService;
  }

  /**
   * Retrieves all ExtraFeatures from the database.
   *
   * @return Return a list of ExtraFeatures or a status indicating no content if none exist.
   */
  @GetMapping
  @Operation(summary = "Get all ExtraFeatures", description = "Retrieve all ExtraFeatures available in the database.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all ExtraFeatures."),
      @ApiResponse(responseCode = "204", description = "No ExtraFeatures available to display.")
  })
  public ResponseEntity<List<ExtraFeature>> getAll() {
    ResponseEntity<List<ExtraFeature>> response;
    List<ExtraFeature> extraFeatures = new ArrayList<>(extraFeatureService.getAllExtraFeatures());
    if (extraFeatures.isEmpty()) {
      logger.warn("There is no ExtraFeature.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all ExtraFeatures.");
      response = new ResponseEntity<>(extraFeatures, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single ExtraFeature based on id.
   *
   * @param id The id of the ExtraFeature
   * @return Return a single ExtraFeature based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single ExtraFeature.", description = "Get a single JSON object with the ExtraFeature.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The ExtraFeature return in the response body."),
      @ApiResponse(responseCode = "404", description = "No ExtraFeature are available, not found.", content = @Content)
  })
  public ResponseEntity<ExtraFeature> getOne(@PathVariable Integer id) {
    ResponseEntity<ExtraFeature> response;
    Optional<ExtraFeature> extraFeature = extraFeatureService.getExtraFeature(id);
    if (extraFeature.isPresent()) {
      logger.info("Returning a single Extra Feature.");
      response = new ResponseEntity<>(extraFeature.get(), HttpStatus.OK);
    } else {
      logger.warn("No Extra Feature with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a new ExtraFeature to the database.
   *
   * @param extraFeature The ExtraFeature to be added.
   * @return Return a ResponseEntity containing the added ExtraFeature or a Bad Request status.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new ExtraFeature",
      description = "Creates a new ExtraFeature. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "ExtraFeature created successfully."),
      @ApiResponse(responseCode = "400", description = "Invalid ExtraFeature data provided.")
  })
  public ResponseEntity<ExtraFeature> addOne(@RequestBody ExtraFeature extraFeature) {
    ResponseEntity<ExtraFeature> response;
    if (extraFeature.isValid()) {
      logger.info("Extra feature added");
      extraFeatureService.addExtraFeature(extraFeature);
      response = new ResponseEntity<>(extraFeature, HttpStatus.OK);
    } else {
      logger.warn("Extra feature is invalid.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing ExtraFeature
   *
   * @param id                  The id of the ExtraFeature to be updated
   * @param updatedExtraFeature The ExtraFeature object to be copied
   * @return Return a ResponseEntity containing the updated ExtraFeature or a Not Found or Bad Request status.
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing ExtraFeature",
      description = "Updates a ExtraFeature by its ID. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "ExtraFeature updated successfully."),
      @ApiResponse(responseCode = "400", description = "Invalid ExtraFeature data provided."),
      @ApiResponse(responseCode = "404", description = "ExtraFeature not found.")
  })
  public ResponseEntity<ExtraFeature> updateLocation(@PathVariable Integer id, @RequestBody ExtraFeature updatedExtraFeature) {
    ResponseEntity<ExtraFeature> response;
    Optional<ExtraFeature> existingExtraFeature = extraFeatureService.getExtraFeature(id);
    if (existingExtraFeature.isEmpty()) {
      logger.warn("Cannot find the location based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (!updatedExtraFeature.isValid()) {
      logger.warn("Location is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Updating a single Locations.");
      updatedExtraFeature.setId(existingExtraFeature.get().getId());
      extraFeatureService.updateExtraFeature(updatedExtraFeature);
      response = new ResponseEntity<>(updatedExtraFeature, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing extraFeature.
   *
   * @param id The id of the extraFeature to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a ExtraFeature",
      description = "Deletes a ExtraFeature by its ID. Requires ROLE_ADMIN authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "ExtraFeature deleted successfully."),
      @ApiResponse(responseCode = "404", description = "ExtraFeature not found.")
  })
  public ResponseEntity<Optional<ExtraFeature>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<ExtraFeature>> response;
    Optional<ExtraFeature> existingExtraFeature = extraFeatureService.getExtraFeature(id);

    if (existingExtraFeature.isPresent()) {
      logger.info("Extra Feature deleted");
      extraFeatureService.deleteExtraFeatureById(id);
      response = new ResponseEntity<>(existingExtraFeature, HttpStatus.OK);
    } else {
      logger.warn("Extra Feature not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
