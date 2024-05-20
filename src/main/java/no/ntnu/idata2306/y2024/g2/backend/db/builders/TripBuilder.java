package no.ntnu.idata2306.y2024.g2.backend.db.builders;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.*;

import java.util.Set;

public class TripBuilder {
  private Flight leaveInitialFlight;
  private Flight leaveArrivalFlight;
  private Flight returnInitialFlight;
  private Flight returnArrivalFlight;
  private Set<Price> prices;
  private Set<ClassType> classTypes;
  private Set<ExtraFeature> extraFeatures;
  private Set<Flight> leaveFlightIntervals;
  private Set<Flight> returnFlightIntervals;

  public TripBuilder setLeaveInitialFlight(Flight leaveInitialFlight) {
    this.leaveInitialFlight = leaveInitialFlight;
    return this;
  }

  public TripBuilder setLeaveArrivalFlight(Flight leaveArrivalFlight) {
    this.leaveArrivalFlight = leaveArrivalFlight;
    return this;
  }

  public TripBuilder setReturnArrivalFlight(Flight returnArrivalFlight) {
    this.returnArrivalFlight = returnArrivalFlight;
    return this;
  }

  public TripBuilder setReturnInitialFlight(Flight returnInitialFlight) {
    this.returnInitialFlight = returnInitialFlight;
    return this;
  }

  public TripBuilder setPrices(Set<Price> prices) {
    this.prices = prices;
    return this;
  }

  public TripBuilder setClassTypes(Set<ClassType> classTypes) {
    this.classTypes = classTypes;
    return this;
  }

  public TripBuilder setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    this.extraFeatures = extraFeatures;
    return this;
  }

  public TripBuilder setLeaveFlightIntervals(Set<Flight> leaveFlightIntervals) {
    this.leaveFlightIntervals = leaveFlightIntervals;
    return this;
  }

  public TripBuilder setReturnFlightIntervals(Set<Flight> returnFlightIntervals) {
    this.returnFlightIntervals = returnFlightIntervals;
    return this;
  }

  public Trip build() {
    return new Trip(leaveInitialFlight, leaveArrivalFlight, returnArrivalFlight, returnInitialFlight, prices, classTypes, extraFeatures, leaveFlightIntervals, returnFlightIntervals);
  }
}
