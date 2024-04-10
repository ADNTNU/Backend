package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.db.services.TripService;

import java.util.Set;

@Entity
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  private Flight leaveInitialFlightId;
  @ManyToOne
  private Flight leaveArrivalFlightId;
  @ManyToOne
  private Flight returnArrivalFlightId;
  @ManyToOne
  private Flight returnInitialFlightId;

  @ManyToMany
  private Set<Price> prices;
  @ManyToMany
  private Set<ClassType> classTypes;
  @ManyToMany
  private Set<ExtraFeature> extraFeatures;
  @ManyToMany
  @Column(nullable = true)
  private Set<Flight> departureFlightInterval;
  @ManyToMany
  @Column(nullable = true)
  private Set<Flight> returnFlightIntervals;

  public Trip(){}

  public Trip(Flight leaveInitialFlightId, Flight leaveArrivalFlightId, Flight returnArrivalFlightId, Flight returnInitialFlightId, Set<Price> prices, Set<ClassType> classTypes, Set<ExtraFeature> extraFeatures, Set<Flight> departureFlightInterval, Set<Flight> returnFlightIntervals) {
    setLeaveInitialFlightId(leaveInitialFlightId);
    setLeaveArrivalFlightId(leaveArrivalFlightId);
    setReturnArrivalFlightId(returnArrivalFlightId);
    setReturnInitialFlightId(returnInitialFlightId);
    setPrices(prices);
    setExtraFeatures(extraFeatures);
    setDepartureFlightInterval(departureFlightInterval);
    setReturnFlightIntervals(returnFlightIntervals);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Flight getLeaveInitialFlightId() {
    return leaveInitialFlightId;
  }

  public void setLeaveInitialFlightId(Flight leaveInitialFlightId) {
    this.leaveInitialFlightId = leaveInitialFlightId;
  }

  public Flight getLeaveArrivalFlightId() {
    return leaveArrivalFlightId;
  }

  public void setLeaveArrivalFlightId(Flight leaveArrivalFlightId) {
    this.leaveArrivalFlightId = leaveArrivalFlightId;
  }

  public Flight getReturnArrivalFlightId() {
    return returnArrivalFlightId;
  }

  public void setReturnArrivalFlightId(Flight returnArrivalFlightId) {
    this.returnArrivalFlightId = returnArrivalFlightId;
  }

  public Flight getReturnInitialFlightId() {
    return returnInitialFlightId;
  }

  public void setReturnInitialFlightId(Flight returnInitialFlightId) {
    this.returnInitialFlightId = returnInitialFlightId;
  }

  public Set<Price> getPrices() {
    return prices;
  }

  public void setPrices(Set<Price> prices) {
    this.prices = prices;
  }

  public Set<ClassType> getClassTypes() {
    return classTypes;
  }

  public void setClassTypes(Set<ClassType> classTypes) {
    this.classTypes = classTypes;
  }

  public Set<ExtraFeature> getExtraFeatures() {
    return extraFeatures;
  }

  public void setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    this.extraFeatures = extraFeatures;
  }

  public Set<Flight> getDepartureFlightInterval() {
    return departureFlightInterval;
  }

  public void setDepartureFlightInterval(Set<Flight> departureFlightInterval) {
    this.departureFlightInterval = departureFlightInterval;
  }

  public Set<Flight> getReturnFlightIntervals() {
    return returnFlightIntervals;
  }

  public void setReturnFlightIntervals(Set<Flight> returnFlightIntervals) {
    this.returnFlightIntervals = returnFlightIntervals;
  }
}
