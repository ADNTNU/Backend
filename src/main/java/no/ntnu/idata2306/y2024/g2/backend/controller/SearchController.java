package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.fasterxml.jackson.annotation.JsonView;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.AutocompleteLocation;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.LocationType;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.TripSearchResult;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirportService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.LocationService;
import no.ntnu.idata2306.y2024.g2.backend.db.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a rest controller for locations entities.
 * Provides CRUD operations.
 *
 * @author Anders Lund
 * @version 18.05.2024
 */
@RestController
@CrossOrigin
@RequestMapping("search")
@Tag(name = "Search API")
public class SearchController {

  private final TripService tripService;
  private final LocationService locationService;
  private final AirportService airportService;

  /**
   * Constructs an instance of SearchController with necessary dependency.
   *
   * @param tripService     The service handling trips.
   * @param locationService The service handling locations.
   * @param airportService  The service handling airports.
   */
  @Autowired
  public SearchController(TripService tripService, LocationService locationService, AirportService airportService) {
    this.tripService = tripService;
    this.locationService = locationService;
    this.airportService = airportService;
  }

  private List<Integer> getAirportIdsByIdOrLocation(Integer airportId, Integer locationId) throws IllegalArgumentException {
    List<Integer> airportIds = new ArrayList<>();
    if (airportId != null) {
      airportService.getAirport(airportId).ifPresent(fromAirport -> airportIds.add(fromAirport.getId()));
    } else if (locationId != null) {
      airportService.getAirportsByLocation(locationService.getLocation(locationId).orElse(null)).forEach(airport -> airportIds.add(airport.getId()));
    } else {
      throw new IllegalArgumentException("Either airportId or locationId must be provided");
    }
    return airportIds;
  }

  /**
   * Performs a complex search for trips based on various criteria.
   *
   * @param fromAirportId  The ID of the departure airport.
   * @param fromLocationId The ID of the departure location.
   * @param toAirportId    The ID of the arrival airport.
   * @param toLocationId   The ID of the arrival location.
   * @param departureDate  The start date for departure.
   * @param returnDate     The end date for return (optional for one-way trips).
   * @param limit          The number of records per page.
   * @param page           The page number.
   * @return ResponseEntity containing the list of trips or an error message.
   */
  @GetMapping
  @Operation(summary = "Search for trips",
      description = "Performs search for one-way or round trips based on departure and arrival locations and dates.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of trips", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid parameters provided", content = @Content),
      @ApiResponse(responseCode = "404", description = "No trips found matching the criteria", content = @Content)
  })
  @JsonView(Views.Search.class)
  public ResponseEntity<?> search(
      @RequestParam(required = false) Integer fromAirportId,
      @RequestParam(required = false) Integer fromLocationId,
      @RequestParam(required = false) Integer toAirportId,
      @RequestParam(required = false) Integer toLocationId,
      @RequestParam Long departureDate,
      @RequestParam(required = false) Long returnDate,
      @RequestParam(name = "l") Integer limit,
      @RequestParam(name = "p") Integer page
  ) {
    ResponseEntity<?> response;
    List<Integer> fromAirportIds;
    List<Integer> toAirportIds;
    Pair<LocalDateTime, LocalDateTime> parsedDepartureDate;
    Pair<LocalDateTime, LocalDateTime> parsedReturnDate = null;

//    System out the headers of the request

    int dateOffset = 2;

    if (fromAirportId == null && fromLocationId == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either fromAirportId or fromLocationId must be provided");
    }
    if (toAirportId == null && toLocationId == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either toAirportId or toLocationId must be provided");
    }

    AtomicBoolean fromAirportSuccess = new AtomicBoolean(false);
    try {
      fromAirportIds = new ArrayList<>(getAirportIdsByIdOrLocation(fromAirportId, fromLocationId));
      fromAirportSuccess.set(true);
      toAirportIds = new ArrayList<>(getAirportIdsByIdOrLocation(toAirportId, toLocationId));
    } catch (NumberFormatException e) {
      String parameterPrefix = fromAirportSuccess.get() ? "to" : "from";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid " + parameterPrefix + "AirportId or " + parameterPrefix + "LocationId. Must be an integer");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    try {
      LocalDateTime fromDateLower = LocalDateTime.ofInstant(Instant.ofEpochSecond(departureDate), ZoneId.ofOffset("UTC", ZoneOffset.UTC)).withHour(0).withMinute(0).withSecond(0);
      LocalDateTime fromDateUpper = fromDateLower.plusDays(dateOffset);
      parsedDepartureDate = Pair.of(fromDateLower, fromDateUpper);
      if (returnDate != null) {
        LocalDateTime toDateLower = LocalDateTime.ofInstant(Instant.ofEpochSecond(returnDate), ZoneId.ofOffset("UTC", ZoneOffset.UTC)).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime toDateUpper = toDateLower.plusDays(dateOffset);
        parsedReturnDate = Pair.of(toDateLower, toDateUpper);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid fromDate or toDate. Must be epoch unix timestamps");
    }

    Pageable pageable = PageRequest.of(page, limit);

    List<TripSearchResult> trips = new ArrayList<>();
    if (parsedReturnDate != null) {
      trips.addAll(tripService.getRoundTripTripsByAirportIdsAndDateRange(fromAirportIds, toAirportIds, parsedDepartureDate.getFirst(), parsedDepartureDate.getSecond(), parsedReturnDate.getFirst(), parsedReturnDate.getSecond(), pageable));
    } else {
      trips.addAll(tripService.getOneWayTripsByAirportIdsAndDepartureDate(fromAirportIds, toAirportIds, parsedDepartureDate.getFirst(), parsedDepartureDate.getSecond(), pageable));
    }
    if (trips.isEmpty()) {
      response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No trips found");
    } else {
      response = ResponseEntity.ok(trips);
    }
    return response;
  }

  @GetMapping("/autocomplete-locations")
  public ResponseEntity<?> autocompleteLocations() {
    List<AutocompleteLocation> autocompleteLocations = new ArrayList<>();
    List<Location> locations = locationService.getAllLocations();
    List<Airport> airports = airportService.getAllAirports();

    locations.forEach(location -> autocompleteLocations.add(new AutocompleteLocation(location.getId(), location.getName(), LocationType.LOCATION)));
    airports.forEach(airport -> autocompleteLocations.add(new AutocompleteLocation(airport.getId(), airport.getFullName(), LocationType.AIRPORT)));

    if (autocompleteLocations.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No locations found");
    } else {
      return ResponseEntity.ok(autocompleteLocations);
    }
  }
}

