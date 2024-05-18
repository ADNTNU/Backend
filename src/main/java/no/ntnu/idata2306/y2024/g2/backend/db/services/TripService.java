package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

  private final TripRepository tripRepository;

  @Autowired
  public TripService(TripRepository tripRepository){
    this.tripRepository = tripRepository;
  }

  public List<Trip> getAllTrips(){
    List<Trip> trips = new ArrayList<>();
    tripRepository.findAll().forEach(trips::add);
    return trips;
  }

  public Optional<Trip> getTrip(int id){
    return tripRepository.findById(id);
  }

  public List<Trip> getOneWayTripsByAirportIdsAndDepartureDate(List<Integer> departureAirportIds, List<Integer> arrivalAirportIds, LocalDateTime departureDateLower, LocalDateTime departureDateUpper, Pageable pageable){
    return tripRepository.findOneWayTripsByAirportIdsAndDepartureDate(departureAirportIds, departureDateLower, departureDateUpper, arrivalAirportIds, pageable);
  }

  public List<Trip> getRoundTripTripsByAirportIdsAndDateRange(List<Integer> departureAirportIds, List<Integer> arrivalAirportIds, LocalDateTime departureDateLower, LocalDateTime departureDateUpper, LocalDateTime returnDateLower, LocalDateTime returnDateUpper, Pageable pageable){
    return tripRepository.findRoundTripTripsByAirportIdsAndDateRange(departureAirportIds, departureDateLower, departureDateUpper, arrivalAirportIds, returnDateLower, returnDateUpper, pageable);
  }

  public void addTrip(Trip trip){
    tripRepository.save(trip);
  }

  public void updateTrip(Trip trip){
    tripRepository.save(trip);
  }

  public void deleteTrip(Trip trip){
    tripRepository.delete(trip);
  }

  public void deleteTripById(int id){
    tripRepository.deleteById(id);
  }

}
