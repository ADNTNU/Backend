package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Set;

@Entity
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  private int id;
  @ManyToOne
  @NotNull
  @JsonView(Views.Full.class)
  private Flight leaveInitialFlight;
  @ManyToOne
  @JsonView(Views.Full.class)
  private Flight leaveArrivalFlight;
  @ManyToOne
  @JsonView(Views.Full.class)
  private Flight returnArrivalFlight;
  @ManyToOne
  @JsonView(Views.Full.class)
  private Flight returnInitialFlight;

  @ManyToMany
  @NotNull
  @JsonView(Views.Full.class)
  private Set<Price> prices;
  @ManyToMany
  @NotNull
  @JsonView(Views.Full.class)
  private Set<ClassType> classTypes;
  @ManyToMany
  @NotNull
  @JsonView(Views.Full.class)
  private Set<ExtraFeature> extraFeatures;
  @ManyToMany
  @Column(nullable = true)
  @JsonView(Views.Full.class)
  private Set<Flight> departureFlightIntervals;
  @ManyToMany
  @Column(nullable = true)
  @JsonView(Views.Full.class)
  private Set<Flight> returnFlightIntervals;

  public Trip() {
  }

  public Trip(Flight leaveInitialFlight, Flight leaveArrivalFlight, Flight returnArrivalFlight, Flight returnInitialFlight, Set<Price> prices, Set<ClassType> classTypes, Set<ExtraFeature> extraFeatures, Set<Flight> departureFlightIntervals, Set<Flight> returnFlightIntervals) {
    setLeaveInitialFlight(leaveInitialFlight);
    setLeaveArrivalFlight(leaveArrivalFlight);
    setReturnArrivalFlight(returnArrivalFlight);
    setReturnInitialFlight(returnInitialFlight);
    setPrices(prices);
    setExtraFeatures(extraFeatures);
    setDepartureFlightIntervals(departureFlightIntervals);
    setReturnFlightIntervals(returnFlightIntervals);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Flight getLeaveInitialFlight() {
    return leaveInitialFlight;
  }

  public void setLeaveInitialFlight(Flight leaveInitialFlightId) {
    this.leaveInitialFlight = leaveInitialFlightId;
  }

  public Flight getLeaveArrivalFlight() {
    return leaveArrivalFlight;
  }

  public void setLeaveArrivalFlight(Flight leaveArrivalFlightId) {
    this.leaveArrivalFlight = leaveArrivalFlightId;
  }

  public Flight getReturnArrivalFlight() {
    return returnArrivalFlight;
  }

  public void setReturnArrivalFlight(Flight returnArrivalFlightId) {
    this.returnArrivalFlight = returnArrivalFlightId;
  }

  public Flight getReturnInitialFlight() {
    return returnInitialFlight;
  }

  public void setReturnInitialFlight(Flight returnInitialFlightId) {
    this.returnInitialFlight = returnInitialFlightId;
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

  public Set<Flight> getDepartureFlightIntervals() {
    return departureFlightIntervals;
  }

  public void setDepartureFlightIntervals(Set<Flight> departureFlightIntervals) {
    this.departureFlightIntervals = departureFlightIntervals;
  }

  public Set<Flight> getReturnFlightIntervals() {
    return returnFlightIntervals;
  }

  public void setReturnFlightIntervals(Set<Flight> returnFlightIntervals) {
    this.returnFlightIntervals = returnFlightIntervals;
  }
}
