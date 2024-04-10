package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airline;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import no.ntnu.idata2306.y2024.g2.backend.db.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("price")
public class PriceController {
  @Autowired
  private PriceService priceService;

  @GetMapping
  public ResponseEntity<List<Price>> getAll(){
    ResponseEntity<List<Price>> response;
    List<Price> prices = new ArrayList<>();
    priceService.getAllPrices().forEach(prices::add);
    if(prices.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(prices, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody Price price) {
    ResponseEntity<String> response;
    if(price != null){
      priceService.addPrice(price);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
