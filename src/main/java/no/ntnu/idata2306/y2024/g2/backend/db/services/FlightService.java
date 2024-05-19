package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Flight;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing flights.
 * This class provides CRUD operations on {@link Flight} entities through the {@link FlightRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class FlightService {

  private final FlightRepository flightRepository;

  /**
   * Constructs an instance of FlightService with necessary dependency.
   *
   * @param flightRepository The repository handling flight operations.
   */
  @Autowired
  public FlightService(FlightRepository flightRepository){
    this.flightRepository = flightRepository;
  }

  /**
   * Retrieves all flights from the database.
   *
   * @return Return a list of {@link Flight} instances, which may be empty if no flights are found.
   */
  public List<Flight> getAllFlights(){
    List<Flight> flights = new ArrayList<>();
    flightRepository.findAll().forEach(flights::add);
    return flights;
  }

  /**
   * Retrieves a specific flight by its ID.
   *
   * @param id The unique identifier of the flight to retrieve.
   * @return Return an {@link Optional} containing the found flight, or an empty Optional if no flight is found.
   */
  public Optional<Flight> getFlight(int id){
    return flightRepository.findById(id);
  }

  /**
   * Adds a new flight to the database.
   *
   * @param flight The {@link Flight} to be added; must not be null.
   */

  public void addFlight(Flight flight){
    flightRepository.save(flight);
  }

  /**
   * Updates an existing flight in the database.
   * Assumes the flight already exists and updates it based on its ID.
   *
   * @param flight The {@link Flight} to update; must not be null.
   */
  public void updateFlight(Flight flight){
    flightRepository.save(flight);
  }

  /**
   * Deletes a flight from the database.
   *
   * @param flight The {@link Flight} to delete; must not be null.
   */
  public void deleteFlight(Flight flight){
    flightRepository.delete(flight);
  }

  /**
   * Deletes a flight from the database by its ID.
   *
   * @param id The unique identifier of the flight to delete.
   */
  public void deleteFlightById(int id){
    flightRepository.deleteById(id);
  }
}
