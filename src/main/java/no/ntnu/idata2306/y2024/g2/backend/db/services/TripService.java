package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  /**
   * Constructs an instance of TripService with necessary dependency.
   *
   * @param tripRepository The repository handling trip operations.
   */
  @Autowired
  public TripService(TripRepository tripRepository){
    this.tripRepository = tripRepository;
  }

  /**
   * Retrieves all trips from the database.
   *
   * @return Return a list of {@link Trip} entities; this list may be empty if no trips are found.
   */
  public List<Trip> getAllTrips(){
    List<Trip> trips = new ArrayList<>();
    tripRepository.findAll().forEach(trips::add);
    return trips;
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
    tripRepository.deleteById(id);
  }

}
