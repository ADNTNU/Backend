package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for {@link Saved} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of saved entities.
 * This interface can be extended to include more complex queries specific to the saved entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface SavedRepository extends CrudRepository<Saved, Integer> {

  List<User> findByUserId(int userId);

  List<Saved> findSavedsByUser_Id(int id);

  List<Saved> findSavedsByTrip_Id(int id);

}
