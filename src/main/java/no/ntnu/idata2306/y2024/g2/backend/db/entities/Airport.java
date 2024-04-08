package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.*;

@Entity
public class Airport {

  /**
   * TODO In the schema, add so only id is required in locationID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String code;
  private String name;
  @ManyToOne()
  private Location locationId;

  public Airport(){}

  public Airport(String code, String name, Location LocationId){
    setCode(code);
    setName(name);

    setLocationId(locationId);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocationId() {
    return locationId;
  }

  public void setLocationId(Location locationId) {
    this.locationId = locationId;
  }
}
