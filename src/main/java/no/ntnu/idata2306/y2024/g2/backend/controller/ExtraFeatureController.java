package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ExtraFeature;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ExtraFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("extraFeature")
public class ExtraFeatureController {

  @Autowired
  private ExtraFeatureService extraFeatureService;

  @GetMapping
  public ResponseEntity<List<ExtraFeature>> getAll(){
    ResponseEntity<List<ExtraFeature>> response;
    List<ExtraFeature> extraFeatures = new ArrayList<>();
    extraFeatureService.getAllExtraFeatures().forEach(extraFeatures::add);
    if(extraFeatures.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(extraFeatures, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody ExtraFeature extraFeature) {
    ResponseEntity<String> response;
    if(extraFeature != null){
      extraFeatureService.addExtraFeature(extraFeature);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
