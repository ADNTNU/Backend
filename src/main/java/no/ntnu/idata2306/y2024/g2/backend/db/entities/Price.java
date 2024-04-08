package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.*;

@Entity
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  private Provider provider;
  private int price;

}
