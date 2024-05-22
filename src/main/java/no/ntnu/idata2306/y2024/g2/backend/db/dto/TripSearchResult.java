package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import com.fasterxml.jackson.annotation.JsonView;
import no.ntnu.idata2306.y2024.g2.backend.Views;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Price;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a TripSearchResult entity with a unique identifier, leaveStopCount, returnStopCount and minPrice
 * The DTO is used to send search results to the client.
 *
 * @author Anders Lund
 * @version 21.05.2024
 */
public class TripSearchResult extends Trip implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonView(Views.Search.class)
  private final int id;
  @JsonView(Views.Search.class)
  private final int leaveStopCount;

  @JsonView(Views.Search.class)
  private final int returnStopCount;

  @JsonView(Views.Search.class)
  private final Price minPrice;


  /**
   * Construct a new TripSearchResult with the specified trip, leaveStopCount and returnStopCount.
   *
   * @param trip The trip object.
   */
  public TripSearchResult(Trip trip) {
    super(trip.getLeaveInitialFlight(), trip.getLeaveArrivalFlight(), trip.getReturnInitialFlight(), trip.getReturnArrivalFlight(), trip.getPrices(), trip.getClassTypes(), trip.getExtraFeatures(), trip.getDepartureFlightIntervals(), trip.getReturnFlightIntervals());
    if (getLeaveArrivalFlight() != null) {
      this.leaveStopCount = (trip.getLeaveArrivalFlight() != null ? trip.getDepartureFlightIntervals().size() : 0) + 1;
    } else {
      this.leaveStopCount = 0;
    }
    if (getReturnArrivalFlight() != null) {
      this.returnStopCount = (trip.getReturnArrivalFlight() != null ? trip.getReturnFlightIntervals().size() : 0) + 1;
    } else {
      this.returnStopCount = 0;
    }
    this.id = trip.getId();
//    Return the price object whose getPrice returns the smallest value
    this.minPrice = trip.getPrices().stream().min(Comparator.comparingInt(Price::getPrice)).orElse(null);

  }

  /**
   * Return the number of stops on the leave trip.
   *
   * @return The number of stops on the leave trip.
   */
  public long getLeaveStopCount() {
    return leaveStopCount;
  }

  /**
   * Return the number of stops on the return trip.
   *
   * @return The number of stops on the return trip.
   */
  public long getReturnStopCount() {
    return returnStopCount;
  }

  /**
   * Return the minimum price of the trip.
   *
   * @return The minimum price of the trip.
   */
  public Price getMinPrice() {
    return minPrice;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (TripSearchResult) obj;
    return this.getId() == that.getId() &&
        this.getLeaveInitialFlight().equals(that.getLeaveInitialFlight()) &&
        this.getLeaveArrivalFlight().equals(that.getLeaveArrivalFlight()) &&
        this.getReturnInitialFlight().equals(that.getReturnInitialFlight()) &&
        this.getReturnArrivalFlight().equals(that.getReturnArrivalFlight()) &&
        this.getPrices().equals(that.getPrices()) &&
        this.getClassTypes().equals(that.getClassTypes()) &&
        this.getExtraFeatures().equals(that.getExtraFeatures()) &&
        this.getDepartureFlightIntervals().equals(that.getDepartureFlightIntervals()) &&
        this.getReturnFlightIntervals().equals(that.getReturnFlightIntervals()) &&
        this.getLeaveStopCount() == that.getLeaveStopCount() &&
        this.getReturnStopCount() == that.getReturnStopCount();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getLeaveInitialFlight(), getLeaveArrivalFlight(), getReturnArrivalFlight(),
        getReturnInitialFlight(), getPrices(), getClassTypes(), getExtraFeatures(), getDepartureFlightIntervals(),
        getReturnFlightIntervals(), getLeaveStopCount(), getReturnStopCount());
  }

  @Override
  public String toString() {
    return "Trip[" +
        "id=" + getId() + ", " +
        "leaveInitialFlight=" + getLeaveInitialFlight() + ", " +
        "leaveArrivalFlight=" + getLeaveArrivalFlight() + ", " +
        "returnArrivalFlight=" + getReturnArrivalFlight() + ", " +
        "returnInitialFlight=" + getReturnInitialFlight() + ", " +
        "prices=" + getPrices() + ", " +
        "classTypes=" + getClassTypes() + ", " +
        "extraFeatures=" + getExtraFeatures() + ", " +
        "departureFlightIntervals=" + getDepartureFlightIntervals() + ", " +
        "returnFlightIntervals=" + getReturnFlightIntervals() + ", " +
        "leaveStopCount=" + getLeaveStopCount() + ", " +
        "returnStopCount=" + getReturnStopCount() + ']';
  }
}
