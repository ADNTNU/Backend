package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Represents a Airline")
public class Airline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "airlineId", nullable = false)
  @Schema(description = "Unique ID")
  private int id;

  @Column(name = "name", nullable = false)
  @Schema(description = "Name of the Airline")
  private String name;
  //private Blob airplainImage;

  public Airline(){}

  public Airline(String name){
    setName(name);
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    if(name == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

}
