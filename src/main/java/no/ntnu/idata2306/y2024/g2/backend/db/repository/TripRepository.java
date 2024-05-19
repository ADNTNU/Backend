package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for {@link Trip} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of Trip entities.
 * This interface can be extended to include more complex queries specific to the Trip entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface TripRepository extends CrudRepository<Trip, Integer> {
}
