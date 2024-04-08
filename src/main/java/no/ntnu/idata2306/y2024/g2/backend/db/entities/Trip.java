package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  private Flight leaveInitialFlightId;
  @ManyToOne
  private Flight leaveArrivalFlightId;
  @ManyToOne
  private Flight returnArrivalFlightId;
  @ManyToOne
  private Flight returnInitialFlightId;

  @ManyToMany
  private Set<Price> prices;
  @ManyToMany
  private Set<ClassType> classTypes;
  @ManyToMany
  private Set<ExtraFeature> extraFeatures;
  @ManyToMany
  private Set<Flight> departureFlightInterval;
  @ManyToMany
  private Set<Flight> returnFlightIntervals;
}
