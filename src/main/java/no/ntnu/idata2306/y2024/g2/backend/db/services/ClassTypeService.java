package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ClassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing class types.
 * Provides CRUD operations on {@link ClassType} entities through the {@link ClassTypeRepository}.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
@Service
public class ClassTypeService {

  private final ClassTypeRepository classTypeRepository;

  /**
   * Constructs an instance of ClassTypeService with necessary dependency.
   *
   * @param classTypeRepository The repository handling class type operations.
   */
  @Autowired
  public ClassTypeService(ClassTypeRepository classTypeRepository){
    this.classTypeRepository = classTypeRepository;
  }

  /**
   * Retrieves all class types from the database.
   *
   * @return Return a list of {@link ClassType} instances, which may be empty if no class types are found.
   */
  public List<ClassType> getAllClassTypes(){
    List<ClassType> classTypes = new ArrayList<>();
    classTypeRepository.findAll().forEach(classTypes::add);
    return classTypes;
  }

  /**
   * Retrieves a specific class type by its ID.
   *
   * @param id The unique identifier of the class type to retrieve.
   * @return Return an {@link Optional} containing the found class type, or an empty Optional if no class type is found.
   */
  public Optional<ClassType> getClassTypes(int id){
    return classTypeRepository.findById(id);
  }
  /**
   * Adds a new class type to the database.
   *
   * @param classType The {@link ClassType} to be added; must not be null.
   */
  public void addClassTypes(ClassType classType){
    classTypeRepository.save(classType);
  }
  /**
   * Adds a new class type to the database.
   *
   * @param classType The {@link ClassType} to be added; must not be null.
   */
  public void updateClassTypes(ClassType classType){
    classTypeRepository.save(classType);
  }
  /**
   * Deletes a specific class type from the database.
   *
   * @param classType The {@link ClassType} to delete; must not be null.
   */
  public void deleteClassTypes(ClassType classType){
    classTypeRepository.delete(classType);
  }
  /**
   * Deletes a class type from the database by its ID.
   *
   * @param id The unique identifier of the class type to delete.
   */
  public void deleteClassTypesById(int id){
    classTypeRepository.deleteById(id);
  }

}
