package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Role} entities.
 * Extends {@link CrudRepository} to provide basic CRUD operations for the management of role entities.
 * This interface can be extended to include more complex queries specific to the role entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
  Role findOneByName(String name);
}
