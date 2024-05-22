package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serial;
import java.io.Serializable;

public class AutocompleteLocation implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final int id;
  private final String name;
  private final LocationType locationType;

  public AutocompleteLocation(int id, String name, LocationType type) {
    this.id = id;
    this.name = name;
    this.locationType = type;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonGetter("type")
  public String getLocationTypeString() {
    return locationType.toString();
  }
}
