package no.ntnu.idata2306.y2024.g2.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.services.SavedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("saved")
@Tag(name = "User Saves API")
public class SavedController {
  @Autowired
  private SavedService savedService;

  @GetMapping
  @JsonView(Views.Full.class)
  public ResponseEntity<List<Saved>> getAll(){
    ResponseEntity<List<Saved>> response;
    List<Saved> saves = new ArrayList<>();
    savedService.getAllSaves().forEach(saves::add);
    if(saves.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(saves, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  @JsonView(Views.IdOnly.class)
  public ResponseEntity<String> addOne(@RequestBody Saved saved) {
    ResponseEntity<String> response;
    if(saved != null){
      savedService.addSaved(saved);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
