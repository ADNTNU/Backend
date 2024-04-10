package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("provider")
public class ProviderController {

  @Autowired
  private ProviderService providerService;

  @GetMapping
  public ResponseEntity<List<Provider>> getAll(){
    ResponseEntity<List<Provider>> response;
    List<Provider> providers = new ArrayList<>();
    providerService.getAllProviders().forEach(providers::add);
    if(providers.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(providers, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Provider provider) {
    ResponseEntity<String> response;
    if(provider != null){
      providerService.addProvider(provider);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
