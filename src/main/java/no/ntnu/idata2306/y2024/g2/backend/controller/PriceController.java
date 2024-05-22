package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import no.ntnu.idata2306.y2024.g2.backend.db.services.PriceService;
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
 * Represents a rest controller for Price entities.
 * Provides CRUD operations.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("price")
@Tag(name = "Price API")
public class PriceController {

  private final PriceService priceService;
  private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

  /**
   * Constructs an instance of PriceController with necessary dependency.
   *
   * @param priceService The Service handling price operations.
   */
  @Autowired
  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  /**
   * Retrieves all Price entities stored in the database.
   *
   * @return Return a list of all prices or an empty list if no prices are available.
   */
  @GetMapping
  @Operation(summary = "Get all Prices", description = "Retrieve all prices from the database.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved list of prices."),
      @ApiResponse(responseCode = "204", description = "No prices found.")
  })
  public ResponseEntity<List<Price>> getAll() {
    ResponseEntity<List<Price>> response;
    List<Price> prices = new ArrayList<>(priceService.getAllPrices());
    if (prices.isEmpty()) {
      logger.warn("No price found.");
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      logger.info("Returning all prices.");
      response = new ResponseEntity<>(prices, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Return a single price based on id.
   *
   * @param id The id of the price
   * @return Return a single price based on id
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a single Price.", description = "Get a single JSON object with the Price.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The Price return in the response body."),
      @ApiResponse(responseCode = "404", description = "No Price are available, not found.", content = @Content)
  })
  public ResponseEntity<Price> getOne(@PathVariable Integer id) {
    ResponseEntity<Price> response;
    Optional<Price> price = priceService.getPrice(id);
    if (price.isPresent()) {
      logger.info("Returning a single Price.");
      response = new ResponseEntity<>(price.get(), HttpStatus.OK);
    } else {
      logger.warn("No Price with that id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Creates a new Price entity in the database.
   *
   * @param price The Price entity to be added to the database
   * @return Return a ResponseEntity containing the created Price entity and HTTP status.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a new Price",
      description = "Creates a new price. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Price successfully created."),
      @ApiResponse(responseCode = "400", description = "Invalid price data provided.")
  })
  public ResponseEntity<Price> addOne(@RequestBody Price price) {
    ResponseEntity<Price> response;
    if (price.isValid()) {
      logger.info("Adding a single Price.");
      priceService.addPrice(price);
      response = new ResponseEntity<>(price, HttpStatus.OK);
    } else {
      logger.warn("Price is invalid and cannot be added.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing location
   *
   * @param id           The id of the location to be updated
   * @param updatedPrice The location object to be copied
   * @return Return 200 if OK, or 404 if id not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update an existing Price",
      description = "Updates a Price by its ID. Requires ROLE_USER authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Price successfully updated."),
      @ApiResponse(responseCode = "404", description = "Price not found."),
      @ApiResponse(responseCode = "400", description = "Invalid price data provided.")
  })
  public ResponseEntity<Price> updateLocation(@PathVariable Integer id, @RequestBody Price updatedPrice) {
    ResponseEntity<Price> response;
    Optional<Price> existingPrice = priceService.getPrice(id);

    if (existingPrice.isEmpty()) {
      logger.warn("Cannot find the Price based on id.");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (!updatedPrice.isValid()) {
      logger.warn("Price is invalid and cannot be updated.");
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Updating a single Price.");
      updatedPrice.setId(existingPrice.get().getId());
      priceService.updatePrice(updatedPrice);
      response = new ResponseEntity<>(updatedPrice, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete an existing Price.
   *
   * @param id The id of the price to be deleted
   * @return Return 200 if ok, or 404 if not found
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a Price",
      description = "Deletes a price by its ID. Requires ROLE_ADMIN authority.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Price successfully deleted."),
      @ApiResponse(responseCode = "404", description = "Price not found.")
  })
  public ResponseEntity<Optional<Price>> deleteLocation(@PathVariable Integer id) {
    ResponseEntity<Optional<Price>> response;
    Optional<Price> existingPrice = priceService.getPrice(id);
    if (existingPrice.isPresent()) {
      logger.info("Delete price");
      priceService.deletePriceById(id);
      response = new ResponseEntity<>(existingPrice, HttpStatus.OK);
    } else {
      logger.warn("Cant find the price with that id");
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
