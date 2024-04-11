package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  @ManyToOne
  private Airport depatureAirportId;
  @ManyToOne
  private Airport arrivalAirportId;
  @ManyToOne
  private Airline airlineId;
  private Date departureDate;
  private Date arrivalTime;

  public Flight(){}

  public Flight(String name, Airport depatureAirportId, Airport arrivalAirportId, Airline airlineId, Date departureDate, Date arrivalTime){
    setName(name);
    setDepatureAirportId(depatureAirportId);
    setArrivalAirportId(arrivalAirportId);
    setAirlineId(airlineId);
    setDepartureDate(departureDate);
    setArrivalTime(arrivalTime);
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

  public Airport getDepatureAirportId() {
    return depatureAirportId;
  }

  public void setDepatureAirportId(Airport depatureAirportId) {
    this.depatureAirportId = depatureAirportId;
  }

  public Airport getArrivalAirportId() {
    return arrivalAirportId;
  }

  public void setArrivalAirportId(Airport arrivalAirportId) {
    this.arrivalAirportId = arrivalAirportId;
  }

  public Airline getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(Airline airlineId) {
    this.airlineId = airlineId;
  }

  public Date getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(Date departureDate) {
    this.departureDate = departureDate;
  }

  public Date getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Date arrivalTime) {
    this.arrivalTime = arrivalTime;
  }
}
