package no.ntnu.idata2306.y2024.g2.backend.controller;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.services.ClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("classType")
public class ClassTypeController {

  @Autowired
  private ClassTypeService classTypeService;

  @GetMapping
  public ResponseEntity<List<ClassType>> getAll(){
    ResponseEntity<List<ClassType>> response;
    List<ClassType> classTypes = new ArrayList<>();
    classTypeService.getAllClassTypes().forEach(classTypes::add);
    if(classTypes.isEmpty()){
      response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else{
      response = new ResponseEntity<>(classTypes, HttpStatus.OK);
    }
    return response;
  }

  @PostMapping
  public ResponseEntity<String> addOne(@RequestBody ClassType classType) {
    ResponseEntity<String> response;
    if(classType != null){
      classTypeService.addClassType(classType);
      response = new ResponseEntity<>("", HttpStatus.OK);
    }else{
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

}
