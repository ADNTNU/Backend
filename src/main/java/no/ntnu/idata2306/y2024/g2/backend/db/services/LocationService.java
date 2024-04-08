package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
  @Autowired
  private LocationRepository locationRepository;

  public List<Location> getAllLocations(){
    List<Location> locations = new ArrayList<>();
    locationRepository.findAll().forEach(locations::add);
    return locations;
  }

  public Optional<Location> getLocation(int id){
    return locationRepository.findById(id);
  }

  public void addLocation(Location location){
    locationRepository.save(location);
  }

  public void updateLocation(Location location){
    locationRepository.save(location);
  }

  public void deleteLocation(Location location){
    locationRepository.delete(location);
  }

  public void deleteLocationById(int id){
    locationRepository.deleteById(id);
  }

}
