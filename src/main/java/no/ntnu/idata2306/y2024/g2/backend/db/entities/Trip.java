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
  @JsonView(Views.Full.class)
  private Flight leaveInitialFlightId;
  @ManyToOne
  @Schema(description = "The leaveArrivalFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight leaveArrivalFlightId;
  @ManyToOne
  @Schema(description = "The returnArrivalFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight returnArrivalFlightId;
  @ManyToOne
  @Schema(description = "The returnInitialFlightId of the Trip.")
  @JsonView(Views.Full.class)
  private Flight returnInitialFlightId;
  @ManyToMany
  @Schema(description = "The prices of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Price> prices;
  @ManyToMany
  @Schema(description = "The classTypes of the Trip.")
  @JsonView(Views.Full.class)
  private Set<ClassType> classTypes;
  @ManyToMany
  @Schema(description = "The extraFeatures of the Trip.")
  @JsonView(Views.Full.class)
  private Set<ExtraFeature> extraFeatures;
  @ManyToMany
  @Column(nullable = true)
  @Schema(description = "The departureFlightInterval of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Flight> departureFlightInterval;
  @ManyToMany
  @Column(nullable = true)
  @Schema(description = "The returnFlightIntervals of the Trip.")
  @JsonView(Views.Full.class)
  private Set<Flight> returnFlightIntervals;

  /**
   * Default JPA constructor.
   */
  public Trip(){}

  /**
   * Construct a new Airport entity with the specified parameters.
   *
   * @param leaveInitialFlightId The leaveInitialFlightId of this entity.
   * @param leaveArrivalFlightId The leaveArrivalFlightId of this entity.
   * @param returnArrivalFlightId The returnArrivalFlightId of this entity.
   * @param returnInitialFlightId The returnInitialFlightId of this entity.
   * @param prices The prices of this entity.
   * @param classTypes The classTypes of this entity.
   * @param extraFeatures The extraFeatures of this entity.
   * @param departureFlightInterval The departureFlightInterval of this entity.
   * @param returnFlightIntervals The returnFlightIntervals of this entity.
   */
  public Trip(Flight leaveInitialFlightId, Flight leaveArrivalFlightId, Flight returnArrivalFlightId, Flight returnInitialFlightId, Set<Price> prices, Set<ClassType> classTypes, Set<ExtraFeature> extraFeatures, Set<Flight> departureFlightInterval, Set<Flight> returnFlightIntervals) {
    setLeaveInitialFlightId(leaveInitialFlightId);
    setLeaveArrivalFlightId(leaveArrivalFlightId);
    setReturnArrivalFlightId(returnArrivalFlightId);
    setReturnInitialFlightId(returnInitialFlightId);
    setPrices(prices);
    setClassTypes(classTypes);
    setExtraFeatures(extraFeatures);
    setDepartureFlightInterval(departureFlightInterval);
    setReturnFlightIntervals(returnFlightIntervals);
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
  public Flight getLeaveInitialFlightId() {
    return leaveInitialFlightId;
  }

  /**
   * Return the leaveArrivalFlightId of the Trip
   *
   * @return The leaveArrivalFlightId of the entity.
   */
  public Flight getLeaveArrivalFlightId() {
    return leaveArrivalFlightId;
  }

  /**
   * Return the returnArrivalFlightId of the Trip
   *
   * @return The returnArrivalFlightId of the entity.
   */
  public Flight getReturnArrivalFlightId() {
    return returnArrivalFlightId;
  }

  /**
   * Return the returnInitialFlightId of the Trip
   *
   * @return The returnInitialFlightId of the entity.
   */
  public Flight getReturnInitialFlightId() {
    return returnInitialFlightId;
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
  public Set<Flight> getDepartureFlightInterval() {
    return departureFlightInterval;
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
   * Sets the leaveInitialFlightId for this Trip.
   *
   * @param leaveInitialFlightId The new leaveInitialFlightId object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if leaveInitialFlightId is null.
   */
  public void setLeaveInitialFlightId(Flight leaveInitialFlightId) {
    if(leaveInitialFlightId == null){
      throw new IllegalArgumentException("LeaveInitialFlightId cannot be null");
    }
    this.leaveInitialFlightId = leaveInitialFlightId;
  }

  /**
   * Sets the leaveArrivalFlightId for this Trip.
   *
   * @param leaveArrivalFlightId The new leaveArrivalFlightId object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if leaveArrivalFlightId is null.
   */
  public void setLeaveArrivalFlightId(Flight leaveArrivalFlightId) {
    if(leaveArrivalFlightId == null){
      throw new IllegalArgumentException("LeaveArrivalFlightId cannot be null");
    }
    this.leaveArrivalFlightId = leaveArrivalFlightId;
  }

  /**
   * Sets the returnArrivalFlightId for this Trip.
   *
   * @param returnArrivalFlightId The new returnArrivalFlightId object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnArrivalFlightId is null.
   */
  public void setReturnArrivalFlightId(Flight returnArrivalFlightId) {
    if(returnArrivalFlightId == null){
      throw new IllegalArgumentException("ReturnArrivalFlightId cannot be null");
    }
    this.returnArrivalFlightId = returnArrivalFlightId;
  }

  /**
   * Sets the returnInitialFlightId for this Trip.
   *
   * @param returnInitialFlightId The new returnInitialFlightId object of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnInitialFlightId is null.
   */
  public void setReturnInitialFlightId(Flight returnInitialFlightId) {
    if(returnInitialFlightId == null){
      throw new IllegalArgumentException("ReturnInitialFlightId cannot be null");
    }
    this.returnInitialFlightId = returnInitialFlightId;
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
   * Sets the departureFlightInterval for this Trip.
   *
   * @param departureFlightInterval The new departureFlightInterval of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if departureFlightInterval is null.
   */
  public void setDepartureFlightInterval(Set<Flight> departureFlightInterval) {
    if(departureFlightInterval == null){
      throw new IllegalArgumentException("DepartureFlightInterval cannot be null");
    }
    if(departureFlightInterval.isEmpty()){
      throw new IllegalArgumentException("There must be departureFlightInterval in the list");
    }
    this.departureFlightInterval = departureFlightInterval;
  }

  /**
   * Sets the returnFlightIntervals for this Trip.
   *
   * @param returnFlightIntervals The new returnFlightIntervals of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if returnFlightIntervals is null.
   */
  public void setReturnFlightIntervals(Set<Flight> returnFlightIntervals) {
    if(returnFlightIntervals == null){
      throw new IllegalArgumentException("ReturnFlightIntervals cannot be null");
    }
    if(returnFlightIntervals.isEmpty()){
      throw new IllegalArgumentException("There must be returnFlightIntervals in the list");
    }
    this.returnFlightIntervals = returnFlightIntervals;
  }

  /**
   * Checks if the object is a valid User.
   *
   * @return Return true if User is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if(leaveInitialFlightId == null){
      isValid = false;
    }else if (leaveArrivalFlightId == null){
      isValid = false;
    }else if (returnArrivalFlightId == null){
      isValid = false;
    }else if (returnInitialFlightId == null){
      isValid = false;
    }else if (prices.isEmpty()){
      isValid = false;
    }else if (classTypes.isEmpty()){
      isValid = false;
    }else if (extraFeatures.isEmpty()){
      isValid = false;
    }else if (departureFlightInterval.isEmpty()){
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
            Objects.equals(this.leaveInitialFlightId, that.leaveInitialFlightId) &&
            Objects.equals(this.leaveArrivalFlightId, that.leaveArrivalFlightId) &&
            Objects.equals(this.returnArrivalFlightId, that.returnArrivalFlightId) &&
            Objects.equals(this.returnInitialFlightId, that.returnInitialFlightId) &&
            Objects.equals(this.prices, that.prices) &&
            Objects.equals(this.classTypes, that.classTypes) &&
            Objects.equals(this.extraFeatures, that.extraFeatures) &&
            Objects.equals(this.departureFlightInterval, that.departureFlightInterval) &&
            Objects.equals(this.returnFlightIntervals, that.returnFlightIntervals);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, leaveInitialFlightId, leaveArrivalFlightId, returnArrivalFlightId,
            returnInitialFlightId, prices, classTypes, extraFeatures, departureFlightInterval,
            returnFlightIntervals);
  }

  @Override
  public String toString() {
    return "User[" +
            "id=" + id + ", " +
            "leaveInitialFlightId=" + leaveInitialFlightId + ", " +
            "leaveArrivalFlightId=" + leaveArrivalFlightId + ", " +
            "returnArrivalFlightId=" + returnArrivalFlightId + ", " +
            "returnInitialFlightId=" + returnInitialFlightId + ", " +
            "prices=" + prices + ", " +
            "classTypes=" + classTypes + ", " +
            "extraFeatures=" + extraFeatures + ", " +
            "departureFlightInterval=" + departureFlightInterval + ", " +
            "returnFlightIntervals=" + returnFlightIntervals + ']';
  }

}
