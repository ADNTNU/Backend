package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.time.LocalDateTime;
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
  @JsonView({Views.IdOnly.class, Views.Search.class})
  @Schema(description = "The unique identifier of the Flight.")
  private int id;

  @JsonView(Views.Search.class)
  @Column(nullable = false)
  @Schema(description = "The name of the Flight.")
  private String name;
  @ManyToOne
  @JsonView(Views.Search.class)
  @Schema(description = "The departureAirport of the Flight.")
  private Airport departureAirport;
  @ManyToOne
  @JsonView(Views.Search.class)
  @Schema(description = "The arrivalAirport of the Flight.")
  private Airport arrivalAirport;
  @ManyToOne
  @JsonView(Views.Search.class)
  @Schema(description = "The airline of the Flight.")
  private Airline airline;

  @JsonView(Views.Search.class)
  @Column(nullable = false)
  @Schema(description = "The departureDate of the Flight.")
  private LocalDateTime departureDate;

  @JsonView(Views.Search.class)
  @Column(nullable = false)
  @Schema(description = "The arrivalTime of the Flight.")
  private LocalDateTime arrivalDate;

  /**
   * Default JPA constructor.
   */
  public Flight() {
  }

  /**
   * Construct a new Flight entity with the specified parameters.
   *
   * @param name               The name of the FLight.
   * @param departureAirportId The departureAirportId of the FLight.
   * @param arrivalAirportId   The arrivalAirportId of the FLight.
   * @param airline            The airlineId of the FLight.
   * @param departureDate      The departureDate of the FLight.
   * @param arrivalDate        The arrivalDate of the FLight.
   */
  public Flight(String name, Airport departureAirportId, Airport arrivalAirportId, Airline airline, LocalDateTime departureDate, LocalDateTime arrivalDate) {
    setName(name);
    setDepartureAirport(departureAirportId);
    setArrivalAirport(arrivalAirportId);
    setAirline(airline);
    setDepartureDate(departureDate);
    setArrivalDate(arrivalDate);
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
  public Airport getDepartureAirport() {
    return departureAirport;
  }

  /**
   * Return the arrivalAirportId of the Flight
   *
   * @return The arrivalAirportId of the entity.
   */
  public Airport getArrivalAirport() {
    return arrivalAirport;
  }

  /**
   * Return the airlineId of the Flight
   *
   * @return The airlineId of the entity.
   */
  public Airline getAirline() {
    return airline;
  }

  /**
   * Return the departureDate of the Flight
   *
   * @return The departureDate of the entity.
   */
  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  /**
   * Return the arrivalTime of the Flight
   *
   * @return The arrivalTime of the entity.
   */
  public LocalDateTime getArrivalDate() {
    return arrivalDate;
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
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

  /**
   * Sets the departureAirportId for this Flight.
   *
   * @param departureAirport The new departureAirportId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureAirportId is null.
   */
  public void setDepartureAirport(Airport departureAirport) {
    if (departureAirport == null) {
      throw new IllegalArgumentException("DepartureAirport cannot be null");
    }
    this.departureAirport = departureAirport;
  }

  /**
   * Sets the arrivalAirportId for this Flight.
   *
   * @param arrivalAirport The new arrivalAirportId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if arrivalAirportId is null.
   */
  public void setArrivalAirport(Airport arrivalAirport) {
    if (arrivalAirport == null) {
      throw new IllegalArgumentException("ArrivalAirportId cannot be null");
    }
    this.arrivalAirport = arrivalAirport;
  }

  /**
   * Sets the airlineId for this Flight.
   *
   * @param airlineId The new airlineId of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if airlineId is null.
   */
  public void setAirline(Airline airlineId) {
    if (airlineId == null) {
      throw new IllegalArgumentException("AirlineId cannot be null");
    }
    this.airline = airlineId;
  }

  /**
   * Sets the departureDate for this Flight.
   *
   * @param departureDate The new departureDate of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureDate is null.
   */
  public void setDepartureDate(LocalDateTime departureDate) {
    if (departureDate == null) {
      throw new IllegalArgumentException("DepartureDate cannot be null");
    }
    this.departureDate = departureDate;
  }

  /**
   * Sets the arrivalTime for this Flight.
   *
   * @param arrivalDate The new arrivalTime of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if arrivalTime is null.
   */
  public void setArrivalDate(LocalDateTime arrivalDate) {
    if (arrivalDate == null) {
      throw new IllegalArgumentException("ArrivalTime cannot be null");
    }
    this.arrivalDate = arrivalDate;
  }

  /**
   * Checks if the object is a valid Saved.
   *
   * @return Return true if Saved is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid() {
    boolean isValid;
    if (name == null || name.isEmpty() || name.isBlank()) {
      isValid = false;
    } else if (departureAirport == null) {
      isValid = false;
    } else if (arrivalAirport == null) {
      isValid = false;
    } else if (airline == null) {
      isValid = false;
    } else if (departureDate == null) {
      isValid = false;
    } else if (arrivalDate == null) {
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
    var that = (Flight) obj;
    return this.id == that.id &&
        Objects.equals(this.name, that.name) &&
        Objects.equals(this.departureAirport, that.departureAirport) &&
        Objects.equals(this.arrivalAirport, that.arrivalAirport) &&
        Objects.equals(this.airline, that.airline) &&
        Objects.equals(this.departureDate, that.departureDate) &&
        Objects.equals(this.arrivalDate, that.arrivalDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, departureAirport, arrivalAirport,
        airline, departureDate, arrivalDate);
  }

  @Override
  public String toString() {
    return "Flight[" +
        "id=" + id + ", " +
        "name=" + name + ", " +
        "departureAirport=" + departureAirport + ", " +
        "arrivalAirport=" + arrivalAirport + ", " +
        "airlineId=" + airline + ", " +
        "departureDate=" + departureDate + ", " +
        "arrivalDate=" + arrivalDate + ']';
  }

}
