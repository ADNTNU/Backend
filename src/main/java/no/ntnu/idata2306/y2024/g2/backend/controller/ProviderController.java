package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ProviderService;
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
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("provider")
@Tag(name = "Provider API")
public class ProviderController {

  private final ProviderService providerService;
  private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

  /**
   * Constructs an instance of ProviderController with necessary dependency.
   *
   * @param providerService The Service handling provider operations.
   */
  @Autowired
  public ProviderController(ProviderService providerService){
    this.providerService = providerService;
  }

  /**
   * Fetches all providers from the database.
   *
   * @return Return a list of all providers or an empty list if no providers are found.
   */
  @GetMapping
  @Operation(summary = "Retrieve all providers", description = "Fetches a list of all providers from the database.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Providers retrieved successfully", content = @Content),
          @ApiResponse(responseCode = "204", description = "No providers available", content = @Content)
  })
  public ResponseEntity<List<Provider>> getAll(){
    ResponseEntity<List<Provider>> response;
    List<Provider> providers = new ArrayList<>(providerService.getAllProviders());
    if(providers.isEmpty()){
      logger.warn("There is no providers.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      logger.info("Returning all providers.");
      response = new ResponseEntity<>(providers, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single Provider based on id.
   *
   * @param id The id of the Provider
   * @return Return a single Provider based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Provider.", description = "Get a single JSON object with the Provider.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "The Provider return in the response body."),
          @ApiResponse(responseCode = "404", description = "No Provider are available, not found.", content = @Content)
  })
  public ResponseEntity<Provider> getOne(@PathVariable Integer id) {
    ResponseEntity<Provider> response;
    Optional<Provider> provider = providerService.getProvider(id);
    if (provider.isPresent()) {
      logger.info("Returning a single Provider.");
      response = new ResponseEntity<>(provider.get(), HttpStatus.OK);
    } else {
      logger.warn("No Provider with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds a new Provider.
   *
   * @param provider The Provider to be added
   * @return Return status code 200 if ok
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Provider",
          description = "Creates a new Provider. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Provider created successfully", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid provider data", content = @Content)
  })
  public ResponseEntity<Provider> addOne(@RequestBody Provider provider) {
    ResponseEntity<Provider> response;
    if(provider.isValid()){
      logger.info("Adding a new Provider");
      providerService.addProvider(provider);
      response = new ResponseEntity<>(provider, HttpStatus.OK);
    }else{
      logger.warn("Bad request, invalid Provider.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing Provider
   *
   * @param id The id of the Provider to be updated
   * @param updatedProvider The Provider object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Provider",
          description = "Updates a Provider by its ID. Requires ROLE_USER authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Provider updated successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "Provider not found", content = @Content),
          @ApiResponse(responseCode = "400", description = "Invalid provider data provided", content = @Content)
  })
  public ResponseEntity<Provider> updateLocation(@PathVariable Integer id, @RequestBody Provider updatedProvider) {
    ResponseEntity<Provider> response;
    Optional<Provider> existingProvider = providerService.getProvider(id);
    if (existingProvider.isEmpty()) {
      logger.warn("Cannot find the Provider based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if(!updatedProvider.isValid()) {
      logger.warn("Provider is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }else {
      logger.info("Updating a single Locations.");
      updatedProvider.setId(existingProvider.get().getId());
      providerService.updateProvider(updatedProvider);
      response = new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing Provider.
   *
   * @param id The id of the location to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Provider",
          description = "Deletes a Provider by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Provider deleted successfully", content = @Content),
          @ApiResponse(responseCode = "404", description = "Provider not found", content = @Content)
  })
  public ResponseEntity<Optional<Provider>> deleteProvider(@PathVariable Integer id) {
    ResponseEntity<Optional<Provider>> response;
    Optional<Provider> existingProvider = providerService.getProvider(id);
    if (existingProvider.isPresent()) {
      logger.info("Deleted provider.");
      providerService.deleteProviderById(id);
      response = new ResponseEntity<>(existingProvider, HttpStatus.OK);
    } else {
      logger.warn("Provider with that id not found.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
