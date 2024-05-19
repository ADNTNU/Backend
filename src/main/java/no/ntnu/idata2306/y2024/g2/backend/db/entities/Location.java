package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Location entity with a unique identifier, country and name.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@Entity
@Schema(description = "Represents a Location.")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Location.")
  @JsonView(Views.IdOnly.class)
  private int id;
  @Column(nullable = false)
  @Schema(description = "The country the location is in.")
  @JsonView({Views.Full.class, Views.NoId.class})
  private String country;
  @Column(nullable = false)
  @Schema(description = "The name of the Location.")
  @JsonView({Views.Full.class, Views.NoId.class})
  private String name;
  @Lob
  @Column(nullable = true, columnDefinition = "TEXT")
  @Schema(description = "The image of the Location.")
  @JsonView({Views.Full.class, Views.NoId.class})
  private String image;

  /**
   * Default JPA constructor.
   */
  public Location(){
  }

  /**
   * Construct a new Location entity with the specified country, name
   * and image.
   *
   * @param country The name of the country.
   * @param name The name of the Location.
   * @param image The image of the Location.
   */
  public Location(String country, String name, String image){
    setCountry(country);
    setName(name);
    setImage(image);
  }

  /**
   * Return the unique identifier of the Location.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the country of the Location.
   *
   * @return The country of the entity.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Return the name of the Location.
   *
   * @return The name of the entity.
   */
  public String getName() {
    return name;
  }

  /**
   * Return the
   *
   * @return
   */
  public String getImage() {
    return image;
  }

  /**
   * Sets the unique identifier for this Location.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than 0.
   */
  public void setId(int id){
    if (id < 0) {
      throw new IllegalArgumentException("Cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the country for this Location.
   *
   * @param country The new country of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if country is empty or null.
   */
  public void setCountry(String country) {
    if(country == null){
      throw new IllegalArgumentException("Country cannot be null");
    }
    if(country.isEmpty() || country.isBlank()){
      throw new IllegalArgumentException("Country cannot be blank");
    }
    this.country = country;
  }

  /**
   * Sets the name for this Location.
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
   * Sets the image of this Location.
   *
   * @param image The new image of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if name is empty or null.
   */
  public void setImage(String image) {
    if(image == null){
      throw new IllegalArgumentException("Image cannot be null");
    }
    if(image.isEmpty() || image.isBlank()){
      throw new IllegalArgumentException("Image cannot be blank");
    }
    this.image = image;
  }

  /**
   * Checks if the object is a valid Location.
   *
   * @return Return true if Location is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if(( country != null && !country.isBlank() && !country.isEmpty()) || (  name != null && !name.isBlank() && !name.isEmpty())){
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Location) obj;
    return this.id == that.id &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.country, that.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, country, name);
  }

  @Override
  public String toString() {
    return "Airline[" +
            "id=" + id + ", " +
            "country=" + country + ", " +
            "name=" + name + ']';
  }

}
