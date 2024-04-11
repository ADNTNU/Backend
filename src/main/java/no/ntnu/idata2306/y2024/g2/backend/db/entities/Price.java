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

  public Price(){}

  public Price(Provider provider, int price){
    setProvider(provider);
    setPrice(price);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Provider getProvider() {
    return provider;
  }

  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
