package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

  private final AirportRepository airportRepository;

  @Autowired
  public AirportService(AirportRepository airportRepository){
    this.airportRepository = airportRepository;
  }

  public List<Airport> getAllAirports(){
    List<Airport> airports = new ArrayList<>();
    airportRepository.findAll().forEach(airports::add);
    return airports;
  }

  public Optional<Airport> getAirport(int id){
    return airportRepository.findById(id);
  }

  public List<Airport> getAirportsByLocation(Location location) {
    return airportRepository.findByLocation(location);
  }

  public void addAirport(Airport airport){
    airportRepository.save(airport);
  }

  public void updateAirport(Airport airport){
    airportRepository.save(airport);
  }

  public void deleteAirport(Airport airport){
    airportRepository.delete(airport);
  }

  public void deleteAirportById(int id){
    airportRepository.deleteById(id);
  }

}
