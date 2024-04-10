package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

  @Autowired
  private ProviderRepository providerRepository;

  public List<Provider> getAllProviders(){
    List<Provider> providers = new ArrayList<>();
    providerRepository.findAll().forEach(providers::add);
    return providers;
  }

  public Optional<Provider> getProvider(int id){
    return providerRepository.findById(id);
  }

  public void addProvider(Provider provider){
    providerRepository.save(provider);
  }

  public void updateProvider(Provider provider){
    providerRepository.save(provider);
  }

  public void deleteProvider(Provider provider){
    providerRepository.delete(provider);
  }

  public void deleteProviderById(int id){
    providerRepository.deleteById(id);
  }

}
