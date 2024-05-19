package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
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
public class AirPortService {

  private final AirportRepository airportRepository;

  /**
   * Constructs an instance of AirPortService with necessary dependency.
   *
   * @param airportRepository The repository handling airport operations.
   */
  @Autowired
  public AirPortService(AirportRepository airportRepository){
    this.airportRepository = airportRepository;
  }

  /**
   * Retrieves all airports from the database.
   *
   * @return Return a list of {@link Airport} entities, which may be empty if no airports are found.
   */
  public List<Airport> getAllAirPorts(){
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
  public Optional<Airport> getAirPortById(int id){
    return airportRepository.findById(id);
  }
  /**
   * Adds a new airport to the database.
   *
   * @param airport The {@link Airport} to be added; must not be null.
   */
  public void addAirPort(Airport airport){
    airportRepository.save(airport);
  }
  /**
   * Updates an existing airport in the database.
   * This method assumes the airport exists and will overwrite the existing one based on its ID.
   *
   * @param airport The {@link Airport} to update; must not be null.
   */
  public void updateAirPort(Airport airport){
    airportRepository.save(airport);
  }
  /**
   * Deletes a specific airport from the database.
   *
   * @param airport The {@link Airport} to delete; must not be null.
   */
  public void deleteAirPort(Airport airport){
    airportRepository.delete(airport);
  }
  /**
   * Deletes an airport from the database by its ID.
   *
   * @param id The unique identifier of the airport to delete.
   */
  public void deleteAirPortById(int id){
    airportRepository.deleteById(id);
  }

}
