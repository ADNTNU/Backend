package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClassType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String description;
}
