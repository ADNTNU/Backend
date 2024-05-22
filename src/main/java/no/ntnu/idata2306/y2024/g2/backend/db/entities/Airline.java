package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;

/**
 * Represents an Airline entity with a unique identifier and name.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@Entity
@Schema(description = "Represents a Airline.")
public class Airline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Airline.")
  @JsonView({Views.IdOnly.class, Views.Search.class})
  private int id;

  @Column(nullable = false)
  @Schema(description = "The name of the Airline")
  @JsonView(Views.Search.class)
  private String name;


  @Lob
  @Column(nullable = true, columnDefinition = "TEXT")
  @Schema(description = "The logo of the Airline.")
  @JsonView({Views.Search.class, Views.NoId.class})
  private String logo;

  /**
   * Default JPA constructor.
   */
  public Airline(){}

  /**
   * Construct a new Airline entity with the specified name.
   *
   * @param name The name of the Airline.
   */
  public Airline(String name){
    setName(name);
  }

  /**
   * Return the unique identifier of the Airline.
   *
   * @return The id of the entity.
   */
  public int getId(){
    return id;
  }

  /**
   * Return the name of the Airline.
   *
   * @return The name of the entity.
   */
  public String getName(){
    return name;
  }

  /**
   * Return the logo of the Airline.
   *
   * @return The logo of the entity.
   */
  public String getLogo(){
    return logo;
  }

  /**
   * Sets the name for this Airline.
   *
   * @param name The new name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if name is empty or null.
   */
  public void setName(String name){
    if(name == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

  /**
   * Set the unique identifier for this Airline.
   *
   * @param id The id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is 0 or negative.
   */
  public void setId(int id){
    if(id < 1){
      throw new IllegalArgumentException("id cannot be 0 or less");
    }
    this.id = id;
  }

  /**
   * Set the logo for this Airline.
   *
   * @param logo The logo of this entity.
   */
  public void setLogo(String logo){
    this.logo = logo;
  }

  /**
   * Checks if the object is a valid Airline.
   *
   * @return Return true if Airline is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid;
    if(name == null || name.isEmpty() || name.isBlank()){
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
    var that = (Airline) obj;
    return this.id == that.id &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.logo, that.logo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, logo);
  }

  @Override
  public String toString() {
    return "Airline[" +
            "id=" + id + ", " +
            "name=" + name + ", " +
            "logo=" + logo + ']';
  }

}
