package no.ntnu.idata2306.y2024.g2.backend.db.services;

import jakarta.transaction.Transactional;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing airports.
 * Provides CRUD operations on {@link Airport} entities through the {@link AirportRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class AirportService {

  private final AirportRepository airportRepository;
  private final FlightService flightService;

  /**
   * Constructs an instance of AirPortService with necessary dependency.
   *
   * @param airportRepository The repository handling airport operations.
   */
  @Autowired
  public AirportService(AirportRepository airportRepository, FlightService flightService){
    this.airportRepository = airportRepository;
    this.flightService = flightService;
  }

  /**
   * Retrieves all airports from the database.
   *
   * @return Return a list of {@link Airport} entities, which may be empty if no airports are found.
   */
  public List<Airport> getAllAirports(){
    List<Airport> airports = new ArrayList<>();
    airportRepository.findAll().forEach(airports::add);
    return airports;
  }

  /**
   * Retrieves a specific airport by its ID.
   *
   * @param id The unique identifier of the airport to retrieve.
   * @return Return an {@link Optional} containing the found airport, or an empty Optional if no airport is found.
   */
  public Optional<Airport> getAirport(int id){
    return airportRepository.findById(id);
  }

  public List<Airport> getAirportsByLocation(Location location) {
    return airportRepository.findByLocation(location);
  }


  /**
   * Adds a new airport to the database.
   *
   * @param airport The {@link Airport} to be added; must not be null.
   */
  public void addAirport(Airport airport){
    airportRepository.save(airport);
  }
  /**
   * Updates an existing airport in the database.
   * This method assumes the airport exists and will overwrite the existing one based on its ID.
   *
   * @param airport The {@link Airport} to update; must not be null.
   */
  public void updateAirport(Airport airport){
    airportRepository.save(airport);
  }
  /**
   * Deletes a specific airport from the database.
   *
   * @param airport The {@link Airport} to delete; must not be null.
   */
  public void deleteAirport(Airport airport){
    airportRepository.delete(airport);
  }
  /**
   * Deletes an airport from the database by its ID.
   *
   * @param id The unique identifier of the airport to delete.
   */
  public void deleteAirportById(int id){
    flightService.deleteAirportById(id);
    airportRepository.deleteById(id);
  }

  /**
   * Used for cascade deletion.
   *
   * @param id The id of a Airport.
   */
  @Transactional
  public void deleteLocationById(int id){
    List<Airport> airports = airportRepository.findAirportsByLocation_Id(id);
    if(!airports.isEmpty()){
      airports.forEach(this::deleteAirportAndDependencies);
    }
  }

  private void deleteAirportAndDependencies(Airport airport) {
    deleteAirportById(airport.getId());
  }

}
