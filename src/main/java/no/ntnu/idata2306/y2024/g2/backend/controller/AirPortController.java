package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Airport;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AirPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("airport")
@Tag(name = "Airport API")
public class AirPortController {

  @Autowired
  private AirPortService airPortService;

  @GetMapping
  public ResponseEntity<List<Airport>> getAll(){
    ResponseEntity<List<Airport>> response;
    List<Airport> airports = new ArrayList<>();
    airPortService.getAllAirPorts().forEach(airports::add);
    if(airports.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(airports, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  @JsonView(Views.IdOnly.class)
  public ResponseEntity<String> addOne(@RequestBody Airport airport) {
    ResponseEntity<String> response;
    if(airport != null){
      airPortService.addAirPort(airport);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Location",
          description = "Deletes a location by its ID. Requires ROLE_ADMIN authority.",
          security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
    Optional<Airport> existingAirport = airPortService.getAirPortById(id);
    if (existingAirport.isPresent()) {
      airPortService.deleteAirPortById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
