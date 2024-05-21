package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for {@link Price} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of price entities.
 * This interface can be extended to include more complex queries specific to the price entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface PriceRepository extends CrudRepository<Price, Integer> {
  List<Price> findPricesByProvider_Id(int id);
}
