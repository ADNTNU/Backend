package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for {@link Airport} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of airport entities.
 * This interface can be extended to include more complex queries specific to the airport entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface AirportRepository extends CrudRepository<Airport, Integer> {
  List<Airport> findByLocation(Location location);
}
