package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findUserByEmail(String username);

}
