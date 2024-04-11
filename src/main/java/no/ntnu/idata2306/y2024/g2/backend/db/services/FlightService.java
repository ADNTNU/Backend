package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Flight;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
  @Autowired
  private FlightRepository flightRepository;

  public List<Flight> getAllFlights(){
    List<Flight> flights = new ArrayList<>();
    flightRepository.findAll().forEach(flights::add);
    return flights;
  }

  public Optional<Flight> getFlight(int id){
    return flightRepository.findById(id);
  }

  public void addFlight(Flight flight){
    flightRepository.save(flight);
  }

  public void updateFlight(Flight flight){
    flightRepository.save(flight);
  }

  public void deleteFlight(Flight flight){
    flightRepository.delete(flight);
  }

  public void deleteFlightById(int id){
    flightRepository.deleteById(id);
  }
}
