package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {
  @Autowired
  private AirlineRepository airlineRepository;

  public List<Airline> getAllAirlines(){
    List<Airline> airlineList = new ArrayList<>();
    airlineRepository.findAll().forEach(airlineList::add);
    return airlineList;
  }

  public Optional<Airline> getAirline(int id){
    return airlineRepository.findById(id);
  }

  public void addAirline(Airline airline){
    airlineRepository.save(airline);
  }

  public void updateAirline(Airline airline){
    airlineRepository.save(airline);
  }

  public void deleteAirline(Airline airline){
    airlineRepository.delete(airline);
  }

  public void deleteAirlineById(int id){
    airlineRepository.deleteById(id);
  }

}
