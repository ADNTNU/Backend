package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@link Provider} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of provider entities.
 * This interface can be extended to include more complex queries specific to the provider entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface ProviderRepository extends CrudRepository<Provider, Integer> {
}
