package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.PriceRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

  @Autowired
  private PriceRepository priceRepository;

  public List<Price> getAllPrices(){
    List<Price> prices = new ArrayList<>();
    priceRepository.findAll().forEach(prices::add);
    return prices;
  }

  public Optional<Price> getPrice(int id){
    return priceRepository.findById(id);
  }

  public void addPrice(Price price){
    priceRepository.save(price);
  }

  public void updatePrice(Price price){
    priceRepository.save(price);
  }

  public void deletePrice(Price price){
    priceRepository.delete(price);
  }

  public void deletePriceById(int id){
    priceRepository.deleteById(id);
  }

}
