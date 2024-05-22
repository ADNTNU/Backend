package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.dto.TripSearchResult;
import jakarta.transaction.Transactional;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling trip-related operations.
 * Provides methods to manage {@link Trip} entities via {@link TripRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class TripService {

  private final TripRepository tripRepository;
  private final SavedService savedService;

  /**
   * Constructs an instance of TripService with necessary dependency.
   *
   * @param tripRepository The repository handling trip operations.
   */
  @Autowired
  public TripService(TripRepository tripRepository, SavedService savedService){
    this.savedService = savedService;
    this.tripRepository = tripRepository;
  }

  /**
   * Retrieves all trips from the database.
   *
   * @return Return a list of {@link Trip} entities; this list may be empty if no trips are found.
   */
  public List<Trip> getAllTrips(){
    return new ArrayList<>(tripRepository.findAll());
  }

  /**
   * Retrieves a trip by its ID.
   *
   * @param id The unique identifier of the trip to retrieve.
   * @return Return an {@link Optional} containing the found trip, or an empty Optional if no trip is found.
   */
  public Optional<Trip> getTrip(int id){
    return tripRepository.findById(id);
  }

  public List<TripSearchResult> getOneWayTripsByAirportIdsAndDepartureDate(List<Integer> departureAirportIds, List<Integer> arrivalAirportIds, LocalDateTime departureDateLower, LocalDateTime departureDateUpper, Pageable pageable){
    return tripRepository.findOneWayTripsByAirportIdsAndDepartureDate(departureAirportIds, departureDateLower, departureDateUpper, arrivalAirportIds, pageable);
  }

  public List<TripSearchResult> getRoundTripTripsByAirportIdsAndDateRange(List<Integer> departureAirportIds, List<Integer> arrivalAirportIds, LocalDateTime departureDateLower, LocalDateTime departureDateUpper, LocalDateTime returnDateLower, LocalDateTime returnDateUpper, Pageable pageable){
    return tripRepository.findRoundTripTripsByAirportIdsAndDateRange(departureAirportIds, departureDateLower, departureDateUpper, arrivalAirportIds, returnDateLower, returnDateUpper, pageable);
  }

  /**
   * Adds a new trip to the database.
   *
   * @param trip The {@link Trip} entity to be added; must not be null.
   */
  public void addTrip(Trip trip){
    tripRepository.save(trip);
  }

  /**
   * Updates an existing trip in the database.
   * The trip must already exist and is identified by its ID.
   *
   * @param trip The {@link Trip} entity to update; must not be null.
   */
  public void updateTrip(Trip trip){
    tripRepository.save(trip);
  }

  /**
   * Deletes a trip from the database.
   *
   * @param trip The {@link Trip} entity to delete; must not be null.
   */
  public void deleteTrip(Trip trip){
    tripRepository.delete(trip);
  }

  /**
   * Deletes a trip from the database by its ID.
   *
   * @param id The unique identifier of the trip to delete.
   */
  public void deleteTripById(int id){
    savedService.deleteTripById(id);
    tripRepository.deleteById(id);
  }

  /**
   * Used for cascade deletion.
   *
   * @param id The trip id to be deleted
   */
  @Transactional
  public void deleteFlightById(int id){
    List<Trip> trips = tripRepository.findTripsIncludingFlight(id);
    if(!trips.isEmpty()){
      trips.forEach(this::deleteTripAndDependencies);
    }
  }

  private void deleteTripAndDependencies(Trip trip) {
    deleteTripById(trip.getId());
  }


}
