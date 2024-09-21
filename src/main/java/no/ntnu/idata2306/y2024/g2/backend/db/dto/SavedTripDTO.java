package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;

public class SavedTripDTO {
  private int id;
  private Trip trip;

  @JsonCreator
  public SavedTripDTO(int id, Trip trip) {
    this.id = id;
    this.trip = trip;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

}