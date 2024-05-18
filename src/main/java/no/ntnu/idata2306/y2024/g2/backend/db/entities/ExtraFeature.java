package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;

/**
 * Represents an ExtraFeature entity with a unique identifier and description
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@Entity
@Schema(description = "Represents a ExtraFeature.")
public class ExtraFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the ExtraFeature.")
  @JsonView(Views.IdOnly.class)
  private int id;
  @Column(nullable = false)
  @Schema(description = "The description of the ExtraFeature.")
  private String description;

  /**
   * Default JPA constructor.
   */
  public ExtraFeature(){}

  /**
   * Construct a new ExtraFeature entity with the specified parameters.
   *
   * @param description The description of the ExtraFeature.
   */
  public ExtraFeature(String description){
    setDescription(description);
  }

  /**
   * Return the unique identifier of the ExtraFeature
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the description of the ExtraFeature
   *
   * @return The description of the entity.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the unique identifier for this ExtraFeature.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than 0.
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("Cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the description for this ExtraFeature.
   *
   * @param description The new description for this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if description is empty or null.
   */
  public void setDescription(String description) {
    if(description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }
    if(description.isEmpty() || description.isBlank()){
      throw new IllegalArgumentException("Description cannot be blank");
    }
    this.description = description;
  }

  /**
   * Checks if the object is a valid Airport.
   *
   * @return Return true if Airport is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if (description == null || description.isEmpty() || description.isBlank()) {
      isValid = false;
    }else{
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ExtraFeature) obj;
    return this.id == that.id &&
            Objects.equals(this.description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description);
  }

  @Override
  public String toString() {
    return "ExtraFeature[" +
            "id=" + id + ", " +
            "description=" + description + ']';
  }
}
