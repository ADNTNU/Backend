package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ClassTypeRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

  @Autowired
  private TripRepository TripRepository;

  public List<Trip> getAllTrips(){
    List<Trip> trips = new ArrayList<>();
    TripRepository.findAll().forEach(trips::add);
    return trips;
  }

  public Optional<Trip> getTrip(int id){
    return TripRepository.findById(id);
  }

  public void addTrip(Trip trip){
    TripRepository.save(trip);
  }

  public void updateTrip(Trip trip){
    TripRepository.save(trip);
  }

  public void deleteTrip(Trip trip){
    TripRepository.delete(trip);
  }

  public void deleteTripById(int id){
    TripRepository.deleteById(id);
  }

}
