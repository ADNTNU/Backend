package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Extends the {@link CrudRepository} interface provided by Spring Data to handle CRUD operations.
 * This interface manages database operations for user entities, providing a method to find users by email.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findUserByEmail(String username);

}
