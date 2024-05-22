package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;

/**
 * Represents an Airport entity with a unique identifier, code, name
 * and a location.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 16.05.2024
 */
@Entity
@Schema(description = "Represents a Airport.")
public class Airport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Airport.")
  @JsonView({Views.IdOnly.class, Views.Search.class})
  private int id;
  @Column(nullable = false)
  @Schema(description = "The code of the Airport.")
  @JsonView(Views.Search.class)
  private String code;
  @Column(nullable = false)
  @Schema(description = "The name of the Airport.")
  @JsonView(Views.Search.class)
  private String name;
  @ManyToOne
  @JoinColumn(name = "location_id")
  @Schema(description = "The location the Airport is in.")
  @JsonView(Views.Search.class)
  private Location location;



  /**
   * Default JPA constructor.
   */
  public Airport(){}

  /**
   * Construct a new Airport entity with the specified parameters.
   *
   * @param code The code of the Airport.
   * @param name The name of the Airport.
   * @param location The location of the Airport.
   */
  public Airport(String code, String name, Location location){
    setCode(code);
    setName(name);
    setLocation(location);
  }

  /**
   * Return the unique identifier of the Airport
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the code of the Airport
   *
   * @return The code of the entity.
   */
  public String getCode() {
    return code;
  }

  /**
   * Return the name of the Airport
   *
   * @return The name of the entity.
   */
  public String getName() {
    return name;
  }

  /**
   * Return the location of the Airport
   *
   * @return The location object of the entity.
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Sets the unique identifier for this Airport.
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
   * Sets the code for this Airport.
   *
   * @param code The new code of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if code is empty or null.
   */
  public void setCode(String code) {
    if(code == null){
      throw new IllegalArgumentException("Code cannot be null");
    }
    if(code.isEmpty() || code.isBlank()){
      throw new IllegalArgumentException("Code cannot be blank");
    }
    this.code = code;
  }

  /**
   * Sets the name for this Airport.
   *
   * @param name The new name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if name is empty or null.
   */
  public void setName(String name) {
    if(name == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

  /**
   * Sets the location for this Airport.
   *
   * @param location The new location object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if location is invalid or null.
   */
  public void setLocation(Location location) {
    if(location == null){
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.location = location;
  }

  /**
   * Checks if the object is a valid Airport.
   *
   * @return Return true if Airport is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid;
    if (code == null || code.isEmpty() || code.isBlank()) {
      isValid = false;
    }
    else if (name == null || name.isEmpty() || name.isBlank()) {
      isValid = false;
    }
    else if (location == null ) {
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
    var that = (Airport) obj;
    return this.id == that.id &&
            Objects.equals(this.code, that.code) &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.location, that.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, name, location);
  }

  @Override
  public String toString() {
    return "Airport[" +
            "id=" + id + ", " +
            "country=" + code + ", " +
            "name=" + name + ']';
  }

  public String getFullName() {
    return name + " (" + code + ")";
  }
}
