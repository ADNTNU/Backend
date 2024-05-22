package no.ntnu.idata2306.y2024.g2.backend.db.dto;

public enum LocationType {
  AIRPORT("airport"),
  LOCATION("location");

  private final String type;

  LocationType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
