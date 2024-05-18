package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.time.LocalDateTime;

@Entity
public class Flight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  private int id;
  private String name;
  @ManyToOne
  private Airport departureAirport;
  @ManyToOne
  private Airport arrivalAirport;
  @ManyToOne
  private Airline airlineId;

  private LocalDateTime departureDate;
  private LocalDateTime arrivalDate;

  public Flight(){}

  public Flight(String name, Airport departureAirport, Airport arrivalAirport, Airline airlineId, LocalDateTime departureDate, LocalDateTime arrivalDate){
    setName(name);
    setDepartureAirport(departureAirport);
    setArrivalAirport(arrivalAirport);
    setAirlineId(airlineId);
    setDepartureDate(departureDate);
    setArrivalDate(arrivalDate);
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

  public Airport getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(Airport departureAirport) {
    this.departureAirport = departureAirport;
  }

  public Airport getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(Airport arrivalAirportId) {
    this.arrivalAirport = arrivalAirportId;
  }

  public Airline getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(Airline airlineId) {
    this.airlineId = airlineId;
  }

  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
  }

  public LocalDateTime getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDateTime arrivalTime) {
    this.arrivalDate = arrivalTime;
  }
}
