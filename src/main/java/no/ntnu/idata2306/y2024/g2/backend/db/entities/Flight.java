package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Flight entity with a unique identifier, name, departureAirportId
 * arrivalAirportId, airlineId, departureDate and arrivalTime.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@Entity
@Schema(description = "Represents a Flight.")
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  @Schema(description = "The unique identifier of the Flight.")
  private int id;
  @JsonView(Views.Full.class)
  @Column(nullable = false)
  @Schema(description = "The name of the Flight.")
  private String name;
  @ManyToOne
  @JsonView(Views.Full.class)
  @Schema(description = "The departureAirportId of the Flight.")
  private Airport departureAirportId;
  @ManyToOne
  @JsonView(Views.Full.class)
  @Schema(description = "The arrivalAirportId of the Flight.")
  private Airport arrivalAirportId;
  @ManyToOne
  @JsonView(Views.Full.class)
  @Schema(description = "The airlineId of the Flight.")
  private Airline airlineId;
  @JsonView(Views.Full.class)
  @Column(nullable = false)
  @Schema(description = "The departureDate of the Flight.")
  private Date departureDate;
  @JsonView(Views.Full.class)
  @Column(nullable = false)
  @Schema(description = "The arrivalTime of the Flight.")
  private Date arrivalTime;

  /**
   * Default JPA constructor.
   */
  public Flight(){}

  /**
   * Construct a new Flight entity with the specified parameters.
   *
   * @param name The name of the FLight.
   * @param departureAirportId The departureAirportId of the FLight.
   * @param arrivalAirportId The arrivalAirportId of the FLight.
   * @param airlineId The airlineId of the FLight.
   * @param departureDate The departureDate of the FLight.
   * @param arrivalTime The arrivalTime of the FLight.
   */
  public Flight(String name, Airport departureAirportId, Airport arrivalAirportId, Airline airlineId, Date departureDate, Date arrivalTime){
    setName(name);
    setDepartureAirportId(departureAirportId);
    setArrivalAirportId(arrivalAirportId);
    setAirlineId(airlineId);
    setDepartureDate(departureDate);
    setArrivalTime(arrivalTime);
  }

  /**
   * Return the unique identifier of the Flight.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the name of the Flight
   *
   * @return The name of the entity.
   */
  public String getName() {
    return name;
  }

  /**
   * Return the departureAirportId of the Flight
   *
   * @return The departureAirportId of the entity.
   */
  public Airport getDepartureAirportId() {
    return departureAirportId;
  }

  /**
   * Return the arrivalAirportId of the Flight
   *
   * @return The arrivalAirportId of the entity.
   */
  public Airport getArrivalAirportId() {
    return arrivalAirportId;
  }

  /**
   * Return the airlineId of the Flight
   *
   * @return The airlineId of the entity.
   */
  public Airline getAirlineId() {
    return airlineId;
  }

  /**
   * Return the departureDate of the Flight
   *
   * @return The departureDate of the entity.
   */
  public Date getDepartureDate() {
    return departureDate;
  }

  /**
   * Return the arrivalTime of the Flight
   *
   * @return The arrivalTime of the entity.
   */
  public Date getArrivalTime() {
    return arrivalTime;
  }

  /**
   * Sets the unique identifier for this Flight.
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
   * Sets the name for this Flight.
   *
   * @param name The new name for this entity.
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
   * Sets the departureAirportId for this Flight.
   *
   * @param departureAirportId The new departureAirportId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureAirportId is null.
   */
  public void setDepartureAirportId(Airport departureAirportId) {
    if(departureAirportId == null){
      throw new IllegalArgumentException("DepartureAirportId cannot be null");
    }
    this.departureAirportId = departureAirportId;
  }

  /**
   * Sets the arrivalAirportId for this Flight.
   *
   * @param arrivalAirportId The new arrivalAirportId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if arrivalAirportId is null.
   */
  public void setArrivalAirportId(Airport arrivalAirportId) {
    if(arrivalAirportId == null){
      throw new IllegalArgumentException("ArrivalAirportId cannot be null");
    }
    this.arrivalAirportId = arrivalAirportId;
  }

  /**
   * Sets the airlineId for this Flight.
   *
   * @param airlineId The new airlineId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if airlineId is null.
   */
  public void setAirlineId(Airline airlineId) {
    if(airlineId == null){
      throw new IllegalArgumentException("AirlineId cannot be null");
    }
    this.airlineId = airlineId;
  }

  /**
   * Sets the departureDate for this Flight.
   *
   * @param departureDate The new departureDate of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureDate is null.
   */
  public void setDepartureDate(Date departureDate) {
    if(departureDate == null){
      throw new IllegalArgumentException("DepartureDate cannot be null");
    }
    this.departureDate = departureDate;
  }

  /**
   * Sets the arrivalTime for this Flight.
   *
   * @param arrivalTime The new arrivalTime of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if arrivalTime is null.
   */
  public void setArrivalTime(Date arrivalTime) {
    if(arrivalTime == null){
      throw new IllegalArgumentException("ArrivalTime cannot be null");
    }
    this.arrivalTime = arrivalTime;
  }

  /**
   * Checks if the object is a valid Saved.
   *
   * @return Return true if Saved is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if (name == null || name.isEmpty() || name.isBlank()) {
      isValid = false;
    } else if (departureAirportId == null) {
      isValid = false;
    } else if (arrivalAirportId == null ) {
      isValid = false;
    } else if (airlineId == null ) {
      isValid = false;
    } else if (departureDate == null ) {
      isValid = false;
    } else if (arrivalTime == null ) {
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
    var that = (Flight) obj;
    return this.id == that.id &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.departureAirportId, that.departureAirportId) &&
            Objects.equals(this.arrivalAirportId, that.arrivalAirportId) &&
            Objects.equals(this.airlineId, that.airlineId) &&
            Objects.equals(this.departureDate, that.departureDate) &&
            Objects.equals(this.arrivalTime, that.arrivalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, departureAirportId, arrivalAirportId,
            airlineId, departureDate, arrivalTime);
  }

  @Override
  public String toString() {
    return "Flight[" +
            "id=" + id + ", " +
            "name=" + name + ", " +
            "departureAirportId=" + departureAirportId + ", " +
            "arrivalAirportId=" + arrivalAirportId + ", " +
            "airlineId=" + airlineId + ", " +
            "departureDate=" + departureDate + ", " +
            "arrivalTime=" + arrivalTime + ']';
  }

}
