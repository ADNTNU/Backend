package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a User Save entity with a unique identifier, User, Trip
 * and Date.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@Entity
@Schema(description = "Represents a User saved trip.")
public class Saved {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Saved.")
  private int id;
  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  @Schema(description = "The user of the Saved")
  private User user;
  @ManyToOne
  @Schema(description = "The v of the Saved")
  private Trip trip;
  @Column(nullable = false)
  @Schema(description = "The savedDate of the Saved")
  private Date savedDate;

  /**
   * Default JPA constructor.
   */
  public Saved(){}

  /**
   * Construct a new Saved entity with the specified user, trip
   * and savedDate.
   *
   * @param user The user of the Saved.
   * @param trip The trip of the Saved.
   * @param savedDate The savedDate of the Saved.
   */
  public Saved(User user, Trip trip, Date savedDate) {
    setUser(user);
    setTrip(trip);
    setSavedDate(savedDate);
  }

  /**
   * Return the unique identifier of the Saved.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the user of the Saved.
   *
   * @return The user of the entity.
   */
  public User getUser() {
    return user;
  }

  /**
   * Return the savedDate of the Saved.
   *
   * @return The savedDate of the entity.
   */
  public Date getSavedDate() {
    return savedDate;
  }

  /**
   * Return the trip of the Saved.
   *
   * @return The trip of the entity.
   */
  public Trip getTrip(){
    return trip;
  }

  /**
   * Set the unique identifier for this Saved.
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
   * Sets the user for this Saved.
   *
   * @param user The new user of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if user is null.
   */
  public void setUser(User user) {
    if(user == null){
      throw new IllegalArgumentException("User cannot be null");
    }
    this.user = user;
  }

  /**
   * Sets the trip for this Saved.
   *
   * @param trip The new trip of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if trip is null.
   */
  public void setTrip(Trip trip){
    if(trip == null){
      throw new IllegalArgumentException("Trip cannot be null");
    }
    this.trip = trip;
  }

  /**
   * Sets the savedDate for this Saved.
   *
   * @param savedDate The new trip of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if savedDate is null.
   */
  public void setSavedDate(Date savedDate) {
    if(savedDate == null){
      throw new IllegalArgumentException("SavedDate cannot be null");
    }
    this.savedDate = savedDate;
  }

  /**
   * Checks if the object is a valid Saved.
   *
   * @return Return true if Saved is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if (user == null) {
      isValid = false;
    }
    else if (trip == null) {
      isValid = false;
    }
    else if (savedDate == null ) {
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
    var that = (Saved) obj;
    return this.id == that.id &&
            Objects.equals(this.user, that.user) &&
            Objects.equals(this.trip, that.trip) &&
            Objects.equals(this.savedDate, that.savedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, trip, savedDate);
  }

  @Override
  public String toString() {
    return "Saved[" +
            "id=" + id + ", " +
            "user=" + user + ", " +
            "trip=" + trip + ", " +
            "savedDate=" + savedDate + ']';
  }

}
