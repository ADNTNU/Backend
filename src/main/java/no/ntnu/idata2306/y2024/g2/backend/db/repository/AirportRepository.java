package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Integer> {
  List<Airport> findByLocation(Location location);
}
