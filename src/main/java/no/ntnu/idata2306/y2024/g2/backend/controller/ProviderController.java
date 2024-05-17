package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Provider;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("provider")
@Tag(name = "Provider API")
public class ProviderController {

  @Autowired
  private ProviderService providerService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
