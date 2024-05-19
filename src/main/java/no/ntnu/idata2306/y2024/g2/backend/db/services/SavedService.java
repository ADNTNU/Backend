package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.SavedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing saved entities.
 * This class provides methods to perform CRUD operations on {@link Saved} instances
 * through the {@link SavedRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class SavedService {

  private final SavedRepository savedRepository;

  /**
   * Constructs an instance of SavedService with necessary dependency.
   *
   * @param savedRepository The repository handling saved operations.
   */
  @Autowired
  public SavedService(SavedRepository savedRepository){
    this.savedRepository = savedRepository;
  }

  /**
   * Retrieves all saved entities from the database.
   *
   * @return Return a list of {@link Saved} instances, which may be empty if no entities are found.
   */
  public List<Saved> getAllSaves(){
    List<Saved> saves = new ArrayList<>();
    savedRepository.findAll().forEach(saves::add);
    return saves;
  }

  /**
   * Retrieves a saved entity by its ID.
   *
   * @param id The unique identifier of the saved entity to retrieve.
   * @return Return an {@link Optional} containing the saved entity if found, or an empty Optional if no entity is found.
   */
  public Optional<Saved> getSaved(int id){
    return savedRepository.findById(id);
  }

  /**
   * Adds a new saved entity to the database.
   *
   * @param saved The {@link Saved} instance to add; must not be null.
   */
  public void addSaved(Saved saved){
    savedRepository.save(saved);
  }

  /**
   * Updates an existing saved entity in the database.
   * This method assumes the entity already exists and will be merged based on its ID.
   *
   * @param saved The {@link Saved} instance to update; must not be null.
   */
  public void updateSaved(Saved saved){
    savedRepository.save(saved);
  }

  /**
   * Deletes a saved entity from the database.
   *
   * @param saved The {@link Saved} instance to delete; must not be null.
   */
  public void deleteSaved(Saved saved){
    savedRepository.delete(saved);
  }

  /**
   * Deletes a saved entity by its ID from the database.
   *
   * @param id The unique identifier of the saved entity to delete.
   */
  public void deleteSavesById(int id){
    savedRepository.deleteById(id);
  }
}
