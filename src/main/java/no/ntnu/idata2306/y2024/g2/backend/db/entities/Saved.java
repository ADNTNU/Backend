package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

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
  private Date savedDate;



  public Saved(){}

  public Saved(User user, Trip trip, Date savedDate) {
    setUser(user);
    setTrip(trip);
    setSavedDate(savedDate);
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Date getSavedDate() {
    return savedDate;
  }

  public Trip getTrip(){
    return trip;
  }

  public void setUser(User user) {
    if(user == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.user = user;
  }

  public void setSavedDate(Date savedDate) {
    if(savedDate == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.savedDate = savedDate;
  }

  public void setTrip(Trip trip){
    this.trip = trip;
  }
}
