package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;

/**
 * Represents a Provider entity with a unique identifier and name.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@Entity
@Schema(description = "Represents a Provider.")
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Provider.")
  @JsonView(Views.IdOnly.class)
  private int id;
  @Column(nullable = false)
  @Schema(description = "The name of the Provider.")
  @JsonView(Views.Full.class)
  private String name;

  /**
   * Default JPA constructor.
   */
  public Provider() {
  }

  /**
   * Construct a new Provider entity with a specified name.
   *
   * @param name The name of the Provider.
   */
  public Provider(String name) {
    setName(name);
  }

  /**
   * Return the unique identifier of the Provider.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the name of the Provider.
   *
   * @return The name of the entity.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the unique identifier for this Provider.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than zero.
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("Cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the name of this Provider.
   *
   * @param name The new name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if name is empty, blank or null.
   */
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

  /**
   * Checks if the object is a valid Provider.
   *
   * @return Return true if Provider is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid() {
    boolean isValid;
    if (name == null || name.isEmpty() || name.isBlank()) {
      isValid = false;
    } else {
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Provider) obj;
    return this.id == that.id &&
        Objects.equals(this.name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Provider[" +
        "id=" + id + ", " +
        "name=" + name + ']';
  }
}
