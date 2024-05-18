package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
  List<Flight> findByDepartureAirportAndDepartureDate(Airport fromAirport, Date date);

  List<Flight> findByArrivalAirportAndArrivalDate(Airport toAirport, Date date);
}
