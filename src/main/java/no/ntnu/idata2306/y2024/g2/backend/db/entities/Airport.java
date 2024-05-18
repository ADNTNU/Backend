package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

@Entity
public class Airport {

  /**
   * TODO In the schema, add so only id is required in locationID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  private int id;
  private String code;
  private String name;
  @ManyToOne()
  private Location location;

  public Airport(){}

  public Airport(String code, String name, Location location){
    setCode(code);
    setName(name);
    setLocation(location);
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

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
