package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ExtraFeature;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ExtraFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing extra features.
 * Provides CRUD operations on {@link ExtraFeature} entities through the {@link ExtraFeatureRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class ExtraFeatureService {

  private final ExtraFeatureRepository extraFeatureRepository;

  /**
   * Constructs an instance of ExtraFeatureService with necessary dependency.
   *
   * @param extraFeatureRepository The repository handling extra feature operations.
   */
  @Autowired
  public ExtraFeatureService(ExtraFeatureRepository extraFeatureRepository) {
    this.extraFeatureRepository = extraFeatureRepository;
  }

  /**
   * Retrieves all extra features stored in the database.
   *
   * @return Return a list of {@link ExtraFeature} instances; this list may be empty if no extra features are found.
   */
  public List<ExtraFeature> getAllExtraFeatures() {
    List<ExtraFeature> extraFeatures = new ArrayList<>();
    extraFeatureRepository.findAll().forEach(extraFeatures::add);
    return extraFeatures;
  }

  /**
   * Retrieves a specific extra feature by its ID.
   *
   * @param id The unique identifier of the extra feature to retrieve.
   * @return Return an {@link Optional} containing the found extra feature, or an empty Optional if no extra feature is found.
   */
  public Optional<ExtraFeature> getExtraFeature(int id) {
    return extraFeatureRepository.findById(id);
  }

  /**
   * Adds a new extra feature to the database.
   *
   * @param extraFeature The {@link ExtraFeature} to be added; must not be null.
   */
  public void addExtraFeature(ExtraFeature extraFeature) {
    extraFeatureRepository.save(extraFeature);
  }

  /**
   * Updates an existing extra feature in the database.
   * Assumes the extra feature exists and will overwrite the existing one based on its ID.
   *
   * @param extraFeature The {@link ExtraFeature} to update; must not be null.
   */
  public void updateExtraFeature(ExtraFeature extraFeature) {
    extraFeatureRepository.save(extraFeature);
  }

  /**
   * Deletes a specific extra feature from the database.
   *
   * @param extraFeature The {@link ExtraFeature} to delete; must not be null.
   */
  public void deleteExtraFeature(ExtraFeature extraFeature) {
    extraFeatureRepository.delete(extraFeature);
  }

  /**
   * Deletes an extra feature from the database by its ID.
   *
   * @param id The unique identifier of the extra feature to delete.
   */
  public void deleteExtraFeatureById(int id) {
    extraFeatureRepository.deleteById(id);
  }


}
