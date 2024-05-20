package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;
import java.util.Set;

/**
 * Represents an Trip entity with a unique identifier, leaveInitialFlightId,
 * leaveArrivalFlightId, returnArrivalFlightId, returnInitialFlightId,
 * prices, classTypes, extraFeatures, departureFlightInterval and
 * returnFlightIntervals.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@Entity
@Schema(description = "Represents a Trip.")
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Trip.")
  @JsonView(Views.IdOnly.class)
  private int id;
  @ManyToOne
  @Schema(description = "The leaveInitialFlightId of the Trip.")
  @NotNull
  @JsonView(Views.Full.class)
  private Flight leaveInitialFlight;
  @ManyToOne
  @Schema(description = "The leaveArrivalFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight leaveArrivalFlight;
  @ManyToOne
  @Schema(description = "The returnArrivalFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight returnArrivalFlight;
  @ManyToOne
  @Schema(description = "The returnInitialFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight returnInitialFlight;

  @ManyToMany
  @NotNull
  @Schema(description = "The prices of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Price> prices;
  @ManyToMany
  @NotNull
  @Schema(description = "The classTypes of the Trip.")
  @JsonView(Views.Full.class)
  private Set<ClassType> classTypes;
  @ManyToMany
  @NotNull
  @Schema(description = "The extraFeatures of the Trip.")
  @JsonView(Views.Full.class)
  private Set<ExtraFeature> extraFeatures;
  @ManyToMany
  @Column(nullable = true)
  @Schema(description = "The departureFlightInterval of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Flight> departureFlightIntervals;
  @ManyToMany
  @Column(nullable = true)
  @Schema(description = "The returnFlightIntervals of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Flight> returnFlightIntervals;
  @Schema(description = "The active status of the trip.")
  private boolean active = true;

  /**
   * Default JPA constructor.
   */
  public Trip(){}

  /**
   * Construct a new Airport entity with the specified parameters.
   *
   * @param leaveInitialFlight The leaveInitialFlightId of this entity.
   * @param leaveArrivalFlight The leaveArrivalFlightId of this entity.
   * @param returnArrivalFlight The returnArrivalFlightId of this entity.
   * @param returnInitialFlight The returnInitialFlightId of this entity.
   * @param prices The prices of this entity.
   * @param classTypes The classTypes of this entity.
   * @param extraFeatures The extraFeatures of this entity.
   * @param departureFlightIntervals The departureFlightInterval of this entity.
   * @param returnFlightIntervals The returnFlightIntervals of this entity.
   */
  public Trip(Flight leaveInitialFlight, Flight leaveArrivalFlight, Flight returnArrivalFlight, Flight returnInitialFlight, Set<Price> prices, Set<ClassType> classTypes, Set<ExtraFeature> extraFeatures, Set<Flight> departureFlightIntervals, Set<Flight> returnFlightIntervals) {
    setLeaveInitialFlight(leaveInitialFlight);
    setLeaveArrivalFlight(leaveArrivalFlight);
    setReturnArrivalFlight(returnArrivalFlight);
    setReturnInitialFlight(returnInitialFlight);
    setPrices(prices);
    setClassTypes(classTypes);
    setDepartureFlightIntervals(departureFlightIntervals);
    setReturnFlightIntervals(returnFlightIntervals);
    setExtraFeatures(extraFeatures);
  }

  /**
   * Return the unique identifier of the Trip
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the leaveInitialFlightId of the Trip
   *
   * @return The leaveInitialFlightId of the entity.
   */
  public Flight getLeaveInitialFlight() {
    return leaveInitialFlight;
  }

  /**
   * Return the leaveArrivalFlightId of the Trip
   *
   * @return The leaveArrivalFlightId of the entity.
   */
  public Flight getLeaveArrivalFlight() {
    return leaveArrivalFlight;
  }

  /**
   * Return the returnArrivalFlightId of the Trip
   *
   * @return The returnArrivalFlightId of the entity.
   */
  public Flight getReturnArrivalFlight() {
    return returnArrivalFlight;
  }

  /**
   * Return the returnInitialFlightId of the Trip
   *
   * @return The returnInitialFlightId of the entity.
   */
  public Flight getReturnInitialFlight() {
    return returnInitialFlight;
  }

  /**
   * Return the prices of the Trip
   *
   * @return The prices of the entity.
   */
  public Set<Price> getPrices() {
    return prices;
  }

  /**
   * Return the classTypes of the Trip
   *
   * @return The classTypes of the entity.
   */
  public Set<ClassType> getClassTypes() {
    return classTypes;
  }

  /**
   * Return the extraFeatures of the Trip
   *
   * @return The extraFeatures of the entity.
   */
  public Set<ExtraFeature> getExtraFeatures() {
    return extraFeatures;
  }

  /**
   * Return the departureFlightInterval of the Trip
   *
   * @return The departureFlightInterval of the entity.
   */
  public Set<Flight> getDepartureFlightIntervals() {
    return departureFlightIntervals;
  }

  /**
   * Return the returnFlightIntervals of the Trip
   *
   * @return The returnFlightIntervals of the entity.
   */
  public Set<Flight> getReturnFlightIntervals() {
    return returnFlightIntervals;
  }

  /**
   * Return the active status of the User.
   *
   * @return Return the active status of this entity.
   */
  public boolean isActive() {
    return active;
  }


  /**
   * Sets the unique identifier for this Trip.
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
   * Sets the leaveInitialFlight for this Trip.
   *
   * @param leaveInitialFlight The new leaveInitialFlight object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if leaveInitialFlight is null.
   */
  public void setLeaveInitialFlight(Flight leaveInitialFlight) {
    if(leaveInitialFlight == null){
      throw new IllegalArgumentException("LeaveInitialFlight cannot be null");
    }
    this.leaveInitialFlight = leaveInitialFlight;
  }

  /**
   * Sets the leaveArrivalFlight for this Trip.
   *
   * @param leaveArrivalFlight The new leaveArrivalFlight object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if leaveArrivalFlight is null.
   */
  public void setLeaveArrivalFlight(Flight leaveArrivalFlight) {
   // if(leaveArrivalFlight == null){
   //   throw new IllegalArgumentException("LeaveArrivalFlight cannot be null");
   // }
    this.leaveArrivalFlight = leaveArrivalFlight;
  }

  /**
   * Sets the returnArrivalFlight for this Trip.
   *
   * @param returnArrivalFlight The new returnArrivalFlight object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnArrivalFlight is null.
   */
  public void setReturnArrivalFlight(Flight returnArrivalFlight) {
    //if(returnArrivalFlight == null){
    //  throw new IllegalArgumentException("ReturnArrivalFlight cannot be null");
    //}
    this.returnArrivalFlight = returnArrivalFlight;
  }

  /**
   * Sets the returnInitialFlight for this Trip.
   *
   * @param returnInitialFlight The new returnInitialFlight object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnInitialFlight is null.
   */
  public void setReturnInitialFlight(Flight returnInitialFlight) {
    //if(returnInitialFlight == null){
    //  throw new IllegalArgumentException("ReturnInitialFlight cannot be null");
    //}
    this.returnInitialFlight = returnInitialFlight;
  }

  /**
   * Sets the prices for this Trip.
   *
   * @param prices The new prices of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if prices is null.
   */
  public void setPrices(Set<Price> prices) {
    if(prices == null){
      throw new IllegalArgumentException("Prices cannot be null");
    }
    if(prices.isEmpty()){
      throw new IllegalArgumentException("There must be prices in the list");
    }
    this.prices = prices;
  }

  /**
   * Sets the classTypes for this Trip.
   *
   * @param classTypes The new classTypes of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if classTypes is null.
   */
  public void setClassTypes(Set<ClassType> classTypes) {
    if(classTypes == null){
      throw new IllegalArgumentException("ClassTypes cannot be null");
    }
    if(classTypes.isEmpty()){
      throw new IllegalArgumentException("There must be classTypes in the list");
    }
    this.classTypes = classTypes;
  }

  /**
   * Sets the extraFeatures for this Trip.
   *
   * @param extraFeatures The new extraFeatures of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if extraFeatures is null.
   */
  public void setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    if(extraFeatures == null){
      throw new IllegalArgumentException("ExtraFeatures cannot be null");
    }
    if(extraFeatures.isEmpty()){
      throw new IllegalArgumentException("There must be extraFeatures in the list");
    }
    this.extraFeatures = extraFeatures;
  }

  /**
   * Sets the departureFlightIntervals for this Trip.
   *
   * @param departureFlightIntervals The new departureFlightIntervals of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureFlightIntervals is null.
   */
  public void setDepartureFlightIntervals(Set<Flight> departureFlightIntervals) {
    //if(departureFlightIntervals == null){
    //  throw new IllegalArgumentException("DepartureFlightIntervals cannot be null");
    //}
    //if(departureFlightIntervals.isEmpty()){
    //  throw new IllegalArgumentException("There must be departureFlightIntervals in the list");
    //}
    this.departureFlightIntervals = departureFlightIntervals;
  }

  /**
   * Sets the returnFlightIntervals for this Trip.
   *
   * @param returnFlightIntervals The new returnFlightIntervals of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnFlightIntervals is null.
   */
  public void setReturnFlightIntervals(Set<Flight> returnFlightIntervals) {
    //if(returnFlightIntervals == null){
    //  throw new IllegalArgumentException("ReturnFlightIntervals cannot be null");
    //}
    //if(returnFlightIntervals.isEmpty()){
    //  throw new IllegalArgumentException("There must be returnFlightIntervals in the list");
    //}
    this.returnFlightIntervals = returnFlightIntervals;
  }

  /**
   * Sets the new active status of the Trip.
   *
   * @param active The new active status of this entity.
   */
  public void setActive(boolean active) {
    this.active = active;
  }


  /**
   * Checks if the object is a valid User.
   *
   * @return Return true if User is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if(leaveInitialFlight == null){
      isValid = false;
    }else if (leaveArrivalFlight == null){
      isValid = false;
    }else if (returnArrivalFlight == null){
      isValid = false;
    }else if (returnInitialFlight == null){
      isValid = false;
    }else if (prices.isEmpty()){
      isValid = false;
    }else if (classTypes.isEmpty()){
      isValid = false;
    }else if (extraFeatures.isEmpty()){
      isValid = false;
    }else if (departureFlightIntervals.isEmpty()){
      isValid = false;
    }else if (returnFlightIntervals.isEmpty()){
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
    var that = (Trip) obj;
    return this.id == that.id &&
            Objects.equals(this.leaveInitialFlight, that.leaveInitialFlight) &&
            Objects.equals(this.leaveArrivalFlight, that.leaveArrivalFlight) &&
            Objects.equals(this.returnArrivalFlight, that.returnArrivalFlight) &&
            Objects.equals(this.returnInitialFlight, that.returnInitialFlight) &&
            Objects.equals(this.prices, that.prices) &&
            Objects.equals(this.classTypes, that.classTypes) &&
            Objects.equals(this.extraFeatures, that.extraFeatures) &&
            Objects.equals(this.departureFlightIntervals, that.departureFlightIntervals) &&
            Objects.equals(this.returnFlightIntervals, that.returnFlightIntervals);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, leaveInitialFlight, leaveArrivalFlight, returnArrivalFlight,
            returnInitialFlight, prices, classTypes, extraFeatures, departureFlightIntervals,
            returnFlightIntervals);
  }

  @Override
  public String toString() {
    return "Trip[" +
            "id=" + id + ", " +
            "leaveInitialFlight=" + leaveInitialFlight + ", " +
            "leaveArrivalFlight=" + leaveArrivalFlight + ", " +
            "returnArrivalFlight=" + returnArrivalFlight + ", " +
            "returnInitialFlight=" + returnInitialFlight + ", " +
            "prices=" + prices + ", " +
            "classTypes=" + classTypes + ", " +
            "extraFeatures=" + extraFeatures + ", " +
            "departureFlightIntervals=" + departureFlightIntervals + ", " +
            "returnFlightIntervals=" + returnFlightIntervals + ']';
  }

}
