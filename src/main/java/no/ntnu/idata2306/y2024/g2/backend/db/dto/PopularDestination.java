package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PopularDestination extends Location implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private long flightCount;
  private final int id;

  public PopularDestination(Location location, long flightCount) {
    super(location.getCountry(), location.getName(), location.getImage());
    this.flightCount = flightCount;
    this.id = location.getId();
  }

  public long getFlightCount() {
    return flightCount;
  }

  public void setFlightCount(long flightCount) {
    this.flightCount = flightCount;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Location) obj;
    return this.getId() == that.getId() &&
        Objects.equals(this.getName(), that.getName()) &&
        Objects.equals(this.getCountry(), that.getCountry()) &&
        Objects.equals(this.getImage(), that.getImage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCountry(), getName(), getImage(), getFlightCount());
  }

  @Override
  public String toString() {
    return "Location[" +
        "id=" + getId() + ", " +
        "country=" + getCountry() + ", " +
        "name=" + getName() + ", " +
        "image=" + getImage() + ", " +
        "flightCount=" + getFlightCount() + "]";
  }

}
