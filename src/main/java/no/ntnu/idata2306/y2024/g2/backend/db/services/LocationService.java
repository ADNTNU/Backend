package no.ntnu.idata2306.y2024.g2.backend.db.services;

import jakarta.persistence.EntityNotFoundException;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirportRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link Location} entities.
 * Provides CRUD (Create, Read, Update, Delete) functionality through the {@link LocationRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class LocationService {

  private final LocationRepository locationRepository;
  private final AirportService airportService;

  /**
   * Constructs an instance of LocationService with necessary dependency.
   *
   * @param locationRepository The repository handling location operations.
   */
  @Autowired
  public LocationService(LocationRepository locationRepository, AirportService airportService){
    this.locationRepository = locationRepository;
    this.airportService = airportService;
  }

  /**
   * Retrieves all locations stored in the database.
   *
   * @return Return a list of {@link Location} entities. This list may be empty if no locations are found.
   */
  public List<Location> getAllLocations(){
    List<Location> locations = new ArrayList<>();
    locationRepository.findAll().forEach(locations::add);
    return locations;
  }

  /**
   * Retrieves a location by its unique identifier.
   *
   * @param id The ID of the location to be retrieved.
   * @return Return an {@link Optional} containing the location if found, or an empty Optional if no location is found.
   */
  public Optional<Location> getLocation(int id){
    return locationRepository.findById(id);
  }

  /**
   * Adds a new location to the database.
   *
   * @param location The {@link Location} entity to be added; must not be null.
   */
  public void addLocation(Location location){
    locationRepository.save(location);
  }

  /**
   * Updates an existing location in the database.
   * This operation assumes the location exists and will overwrite the existing location based on its ID.
   *
   * @param location The {@link Location} entity to update; must not be null.
   */
  public void updateLocation(Location location){
    locationRepository.save(location);
  }


  /**
   * Deletes a specific location from the database.
   *
   * @param location The {@link Location} entity to be deleted; must not be null.
   */
  public void deleteLocation(Location location){
    locationRepository.delete(location);
  }

  /**
   * Deletes a location from the database based on its ID.
   *
   * @param id The unique identifier of the location to delete.
   */
  public void deleteLocationById(int id){
    //Need to delete all associated Airports as well
    airportService.deleteLocationById(id);
    locationRepository.deleteById(id);
  }

}
