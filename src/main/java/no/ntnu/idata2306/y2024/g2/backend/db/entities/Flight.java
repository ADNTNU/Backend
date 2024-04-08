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

}
