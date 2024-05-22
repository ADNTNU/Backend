package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ClassTypeService;
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
 * Controller for managing ClassType entities. Provides CRUD functionalities.
 * This controller handles the API requests for operations on ClassTypes.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@RestController
@CrossOrigin
@RequestMapping("classType")
@Tag(name = "ClassType API")
public class ClassTypeController {

  private final ClassTypeService classTypeService;
  private static final Logger logger = LoggerFactory.getLogger(ClassTypeController.class);

  /**
   * Constructs an instance of ClassTypeController with necessary dependency.
   *
   * @param classTypeService The Service handling classType operations.
   */
  @Autowired
  public ClassTypeController(ClassTypeService classTypeService) {
    this.classTypeService = classTypeService;
  }

  /**
   * Return all classTypes
   *
   * @return Return all ClassType.
   */
  @GetMapping
  @Operation(summary = "Get all ClassTypes.", description = "Get an JSON list of all classType.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The classType return in the response body."),
      @ApiResponse(responseCode = "204", description = "No classType are available, none exist.", content = @Content)
  })
  public ResponseEntity<List<ClassType>> getAll() {
    ResponseEntity<List<ClassType>> response;
    List<ClassType> classTypes = new ArrayList<>(classTypeService.getAllClassTypes());
    if (classTypes.isEmpty()) {
      logger.warn("There is no ClassType, not found.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all ClassTypes.");
      response = new ResponseEntity<>(classTypes, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single classType based on id.
   *
   * @param id The id of the classType
   * @return Return a single classType based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single classType.", description = "Get a single JSON object with the classType.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The classType return in the response body."),
      @ApiResponse(responseCode = "404", description = "No classType are available, not found.", content = @Content)
  })
  public ResponseEntity<ClassType> getOne(@PathVariable Integer id) {
    ResponseEntity<ClassType> response;
    Optional<ClassType> classType = classTypeService.getClassTypes(id);
    if (classType.isPresent()) {
      logger.info("Returning a single ClassType.");
      response = new ResponseEntity<>(classType.get(), HttpStatus.OK);
    } else {
      logger.warn("No ClassType with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a new classType.
   *
   * @param classType The classType to be added
   * @return Return status code 200 if ok
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new classType",
      description = "Creates a new classType. Requires ROLE_ADMIN authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The classType is added and returned in the response body."),
      @ApiResponse(responseCode = "400", description = "ClassType is invalid.", content = @Content)
  })
  public ResponseEntity<ClassType> addOne(@RequestBody ClassType classType) {
    ResponseEntity<ClassType> response;
    if (classType.isValid()) {
      logger.info("ClassType is valid and is added.");
      classTypeService.addClassType(classType);
      response = new ResponseEntity<>(classType, HttpStatus.OK);
    } else {
      logger.info("ClassType is invalid, cant be added");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing classType
   *
   * @param id               The id of the classType to be updated
   * @param updatedClassType The classType object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing classType",
      description = "Updates a classType by its ID. Requires ROLE_ADMIN authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The classType is updated."),
      @ApiResponse(responseCode = "400", description = "No classType invalid.", content = @Content),
      @ApiResponse(responseCode = "404", description = "ClassType is found.", content = @Content)
  })
  public ResponseEntity<ClassType> updateLocation(@PathVariable Integer id, @RequestBody ClassType updatedClassType) {
    ResponseEntity<ClassType> response;
    Optional<ClassType> existingClassType = classTypeService.getClassTypes(id);

    if (existingClassType.isEmpty()) {
      logger.warn("Cannot find the classType based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (!updatedClassType.isValid()) {
      logger.warn("ClassType is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Updating a single classType.");
      updatedClassType.setId(existingClassType.get().getId());
      classTypeService.updateClassTypes(updatedClassType);
      response = new ResponseEntity<>(updatedClassType, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing ClassType.
   *
   * @param id The id of the classType to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a classType",
      description = "Deletes a classType by its ID. Requires ROLE_ADMIN authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The classType is updated."),
      @ApiResponse(responseCode = "404", description = "ClassType is found.", content = @Content)
  })
  public ResponseEntity<Optional<ClassType>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<ClassType>> response;
    Optional<ClassType> existingClassType = classTypeService.getClassTypes(id);
    if (existingClassType.isPresent()) {
      logger.info("Deleting ClassType.");
      classTypeService.deleteClassTypesById(id);
      response = new ResponseEntity<>(existingClassType, HttpStatus.OK);
    } else {
      logger.warn("ClassType not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
