package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirPortService {

  @Autowired
  private AirportRepository airportRepository;

  public List<Airport> getAllAirPorts(){
    List<Airport> airports = new ArrayList<>();
    airportRepository.findAll().forEach(airports::add);
    return airports;
  }

  public Optional<Airport> getAirPortById(int id){
    return airportRepository.findById(id);
  }

  public void addAirPort(Airport airport){
    airportRepository.save(airport);
  }

  public void updateAirPort(Airport airport){
    airportRepository.save(airport);
  }

  public void deleteAirPort(Airport airport){
    airportRepository.delete(airport);
  }

  public void deleteAirPortById(int id){
    airportRepository.deleteById(id);
  }

}
