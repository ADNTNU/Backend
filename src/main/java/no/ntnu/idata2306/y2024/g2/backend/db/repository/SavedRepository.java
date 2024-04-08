package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavedRepository extends CrudRepository<Saved, Integer> {

  public List<User> findByUserId(int userId);
}
