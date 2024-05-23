package no.ntnu.idata2306.y2024.g2.backend.db.dto;


import com.fasterxml.jackson.annotation.JsonCreator;

public class UserSaveDTO {
  private final int tripId;

  @JsonCreator
  public UserSaveDTO(int tripId){
    this.tripId = tripId;
  }


  public int getTripId() {
    return tripId;
  }

}
