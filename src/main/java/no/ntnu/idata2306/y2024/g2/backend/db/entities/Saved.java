package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Saved {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;
  @ManyToOne
  private Trip trip;
  private String savedDate;



  public Saved(){}

  public Saved(String savedDate) {
    setSavedDate(savedDate);
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getSavedDate() {
    return savedDate;
  }

  public void setUser(User user) {
    if(user == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.user = user;
  }

  public void setSavedDate(String savedDate) {
    if(savedDate == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(savedDate.isEmpty() || savedDate.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.savedDate = savedDate;
  }
}
