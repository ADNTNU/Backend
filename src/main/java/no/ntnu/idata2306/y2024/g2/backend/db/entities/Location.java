package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String country;
  private String name;
  @Lob
  @Column(nullable = true, columnDefinition = "MEDIUMBLOB")
  private byte[] imageBlob;

  public Location(){
  }

  public int getId() {
    return id;
  }

  public String getCountry() {
    return country;
  }

  public String getName() {
    return name;
  }

  public byte[] getImageBlob() {
    return imageBlob;
  }

  public Location(String country, String name){
    setCountry(country);
    setName(name);
  }

  public void setCountry(String country) {
    if(country == null){
      throw new IllegalArgumentException("Country cannot be null");
    }
    if(country.isEmpty() || country.isBlank()){
      throw new IllegalArgumentException("Country cannot be blank");
    }
    this.country = country;
  }

  public void setName(String name) {
    if(name == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }


}
