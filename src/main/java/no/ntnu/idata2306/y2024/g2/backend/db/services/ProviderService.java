package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing providers.
 * This class provides CRUD operations for {@link Provider} entities through the {@link ProviderRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class ProviderService {

  private final ProviderRepository providerRepository;
  private final PriceService priceService;

  /**
   * Constructs an instance of ProviderService with necessary dependency.
   *
   * @param providerRepository The repository handling provider operations.
   */
  @Autowired
  public ProviderService(ProviderRepository providerRepository, PriceService priceService){
    this.providerRepository = providerRepository;
    this.priceService = priceService;
  }

  /**
   * Retrieves all providers from the database.
   *
   * @return Return a list of {@link Provider} instances, which may be empty if no providers are currently stored.
   */
  public List<Provider> getAllProviders(){
    List<Provider> providers = new ArrayList<>();
    providerRepository.findAll().forEach(providers::add);
    return providers;
  }

  /**
   * Retrieves a provider by its ID.
   *
   * @param id The unique identifier of the provider to retrieve.
   * @return Return an {@link Optional} of {@link Provider} if found, otherwise an empty Optional if no provider is found.
   */
  public Optional<Provider> getProvider(int id){
    return providerRepository.findById(id);
  }

  /**
   * Adds a new provider to the database.
   *
   * @param provider The {@link Provider} to add; must not be null.
   */
  public void addProvider(Provider provider){
    providerRepository.save(provider);
  }

  /**
   * Updates an existing provider in the database.
   * Assumes the provider already exists and will overwrite the existing one based on its ID.
   *
   * @param provider The {@link Provider} to update; must not be null.
   */
  public void updateProvider(Provider provider){
    providerRepository.save(provider);
  }

  /**
   * Deletes a provider from the database.
   *
   * @param provider The {@link Provider} to delete; must not be null.
   */
  public void deleteProvider(Provider provider){
    providerRepository.delete(provider);
  }

  /**
   * Deletes a provider from the database by its ID.
   *
   * @param id The unique identifier of the provider to delete.
   */
  public void deleteProviderById(int id){
    priceService.deleteProviderById(id);
    providerRepository.deleteById(id);
  }

}
