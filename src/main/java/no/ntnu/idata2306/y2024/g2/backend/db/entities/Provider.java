package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import no.ntnu.idata2306.y2024.g2.backend.Views;

@Entity
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  private int id;
  private String name;

  public Provider(){

  }

  public Provider(String name){
    setName(name);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
