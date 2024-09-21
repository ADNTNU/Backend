package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SavedDTO {
  private int id;
  private int tripId;

  @JsonCreator
  public SavedDTO(int id, int tripId) {
    this.id = id;
    this.tripId = tripId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTripId() {
    return tripId;
  }

  public void setTripId(int tripId) {
    this.tripId = tripId;
  }
}
