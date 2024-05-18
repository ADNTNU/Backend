package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ClassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassTypeService {

  @Autowired
  private ClassTypeRepository classTypeRepository;

  public List<ClassType> getAllClassTypes(){
    List<ClassType> classTypes = new ArrayList<>();
    classTypeRepository.findAll().forEach(classTypes::add);
    return classTypes;
  }

  public Optional<ClassType> getClassTypes(int id){
    return classTypeRepository.findById(id);
  }

  public void addClassType(ClassType classType){
    classTypeRepository.save(classType);
  }

  public void updateClassTypes(ClassType classType){
    classTypeRepository.save(classType);
  }

  public void deleteClassTypes(ClassType classType){
    classTypeRepository.delete(classType);
  }

  public void deleteClassTypesById(int id){
    classTypeRepository.deleteById(id);
  }

}
