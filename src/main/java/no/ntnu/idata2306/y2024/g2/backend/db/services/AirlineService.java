package no.ntnu.idata2306.y2024.g2.backend.db.services;

import jakarta.persistence.EntityNotFoundException;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing airlines.
 * Provides CRUD operations on {@link Airline} entities through the {@link AirlineRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class AirlineService {

  private final AirlineRepository airlineRepository;
  private final FlightService flightService;

  /**
   * Constructs an instance of AirlineService with necessary dependency.
   *
   * @param airlineRepository The repository handling airline operations.
   */
  @Autowired
  public AirlineService(AirlineRepository airlineRepository, FlightService flightService){
    this.airlineRepository = airlineRepository;
    this.flightService = flightService;
  }

  /**
   * Retrieves all airlines from the database.
   *
   * @return Return a list of {@link Airline} entities, which may be empty if no airlines are found.
   */
  public List<Airline> getAllAirlines(){
    List<Airline> airlineList = new ArrayList<>();
    airlineRepository.findAll().forEach(airlineList::add);
    return airlineList;
  }

  /**
   * Retrieves an airline by its ID.
   * Throws {@link EntityNotFoundException} if the airline does not exist.
   *
   * @param id The unique identifier of the airline to retrieve.
   * @return Return an {@link Optional} containing the found airline, or an empty Optional if no airline is found.
   * @throws EntityNotFoundException Throws EntityNotFoundException if no airline is found with the given ID.
   */
  public Optional<Airline> getAirline(int id){
    return airlineRepository.findById(id);
  }

  /**
   * Adds a new airline to the database.
   *
   * @param airline The {@link Airline} to be added; must not be null.
   */
  public void addAirline(Airline airline){
    airlineRepository.save(airline);
  }

  /**
   * Updates an existing airline in the database.
   * Assumes the airline already exists and will overwrite the existing one based on its ID.
   *
   * @param airline The {@link Airline} to update; must not be null.
   */
  public void updateAirline(Airline airline){
    airlineRepository.save(airline);
  }

  /**
   * Deletes a specific airline from the database.
   *
   * @param airline The {@link Airline} to delete; must not be null.
   */
  public void deleteAirline(Airline airline){
    airlineRepository.delete(airline);
  }
  /**
   * Deletes an airline from the database by its ID.
   * Throws {@link EntityNotFoundException} if the airline does not exist.
   *
   * @param id The unique identifier of the airline to delete.
   * @throws EntityNotFoundException Throws entityNotFoundException if no airline is found with the given ID.
   */
  public void deleteAirlineById(int id){
    if(!airlineRepository.existsById(id)){
      throw new EntityNotFoundException("Entity with id: " + id + " does not exist");
    }
    flightService.deleteAirlineById(id);
    airlineRepository.deleteById(id);
  }

}
