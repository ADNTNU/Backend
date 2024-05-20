package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
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


@RestController
@RequestMapping("search")
public class SearchController {

  private final TripService tripService;
  private final LocationService locationService;
  private final AirportService airportService;

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

  @GetMapping
//  @JsonView(Views.Search.class)
  public ResponseEntity<?> search(
      @RequestParam(required = false) Integer fromAirportId,
      @RequestParam(required = false) Integer fromLocationId,
      @RequestParam(required = false) Integer toAirportId,
      @RequestParam(required = false) Integer toLocationId,
      @RequestParam Long fromDate,
      @RequestParam(required = false) Long toDate,
      @RequestParam(name = "l") Integer limit,
      @RequestParam(name = "p") Integer page
  ) {
    ResponseEntity<?> response;
    List<Integer> fromAirportIds;
    List<Integer> toAirportIds;
    Pair<LocalDateTime, LocalDateTime> parsedFromDate;
    Pair<LocalDateTime, LocalDateTime> parsedToDate = null;

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
      LocalDateTime fromDateLower = LocalDateTime.ofInstant(Instant.ofEpochSecond(fromDate), ZoneId.ofOffset("UTC", ZoneOffset.UTC)).withHour(0).withMinute(0).withSecond(0);
      LocalDateTime fromDateUpper = fromDateLower.plusDays(dateOffset);
      parsedFromDate = Pair.of(fromDateLower, fromDateUpper);
      if (toDate != null) {
        LocalDateTime toDateLower = LocalDateTime.ofInstant(Instant.ofEpochSecond(toDate), ZoneId.ofOffset("UTC", ZoneOffset.UTC)).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime toDateUpper = toDateLower.plusDays(dateOffset);
        parsedToDate = Pair.of(toDateLower, toDateUpper);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid fromDate or toDate. Must be epoch unix timestamps");
    }

    Pageable pageable = PageRequest.of(page, limit);

    List<Trip> trips = new ArrayList<>();
    if (parsedToDate != null) {
      trips.addAll(tripService.getRoundTripTripsByAirportIdsAndDateRange(fromAirportIds, toAirportIds, parsedFromDate.getFirst(), parsedFromDate.getSecond(), parsedToDate.getFirst(), parsedToDate.getSecond(), pageable));
    } else {
      trips.addAll(tripService.getOneWayTripsByAirportIdsAndDepartureDate(fromAirportIds, toAirportIds, parsedFromDate.getFirst(), parsedFromDate.getSecond(), pageable));
    }
    if (trips.isEmpty()) {
      response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No trips found");
    } else {
      response = ResponseEntity.ok(trips);
    }
    return response;
  }

}

