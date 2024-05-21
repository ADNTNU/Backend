package no.ntnu.idata2306.y2024.g2.backend.db.services;

import jakarta.transaction.Transactional;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing pricing information.
 * Provides CRUD operations for {@link Price} entities via {@link PriceRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class PriceService {

  private final PriceRepository priceRepository;

  /**
   * Constructs an instance of PriceService with necessary dependency.
   *
   * @param priceRepository The repository handling price operations.
   */
  @Autowired
  public PriceService(PriceRepository priceRepository){
    this.priceRepository = priceRepository;
  }

  /**
   * Retrieves all prices from the database.
   *
   * @return Return a list of {@link Price} instances; this list may be empty if no prices are currently stored.
   */
  public List<Price> getAllPrices(){
    List<Price> prices = new ArrayList<>();
    priceRepository.findAll().forEach(prices::add);
    return prices;
  }

  /**
   * Retrieves a specific price by its ID.
   *
   * @param id The unique identifier of the price to retrieve.
   * @return Return an {@link Optional} containing the found price, or an empty Optional if no price is found.
   */
  public Optional<Price> getPrice(int id){
    return priceRepository.findById(id);
  }

  /**
   * Adds a new price to the database.
   *
   * @param price The {@link Price} entity to add; must not be null.
   */
  public void addPrice(Price price){
    priceRepository.save(price);
  }

  /**
   * Updates an existing price in the database.
   * The operation assumes the price exists and will overwrite the existing price based on its ID.
   *
   * @param price The {@link Price} entity to update; must not be null.
   */
  public void updatePrice(Price price){
    priceRepository.save(price);
  }

  /**
   * Deletes a price from the database.
   *
   * @param price The {@link Price} entity to delete; must not be null.
   */
  public void deletePrice(Price price){
    priceRepository.delete(price);
  }


  /**
   * Deletes a price from the database by its ID.
   *
   * @param id The unique identifier of the price to delete.
   */
  public void deletePriceById(int id){
    priceRepository.deleteById(id);
  }

  /**
   * Used for cascade deletion.
   *
   * @param id The id of a Price.
   */
  @Transactional
  public void deleteProviderById(int id){
    List<Price> prices = priceRepository.findPricesByProvider_Id(id);
    if(!prices.isEmpty()){
      prices.forEach(this::deletePriceAndDependencies);
    }
  }

  private void deletePriceAndDependencies(Price price) {
    deletePriceById(price.getId());
  }


}
