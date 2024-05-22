package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import java.time.LocalDateTime;

public class UserSaveDTO {
  private final String email;
  private final int tripId;
  private final LocalDateTime savedDate;

  public UserSaveDTO(String email, int tripId, LocalDateTime savedDate){
    this.email = email;
    this.tripId = tripId;
    this.savedDate = savedDate;
  }

  public String getEmail() {
    return email;
  }

  public int getTripId() {
    return tripId;
  }

  public LocalDateTime getSavedDate() {return savedDate;}
}
