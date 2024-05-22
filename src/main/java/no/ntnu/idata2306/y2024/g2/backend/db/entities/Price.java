package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.Objects;

/**
 * Represents a Price entity with a unique identifier, Provider and price.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 10.04.2024
 */
@Entity
@Schema(description = "Represents a Price.")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the Price.")
  @JsonView(Views.IdOnly.class)
  private int id;

  @ManyToOne
  @JsonView(Views.Full.class)
  @Schema(description = "The Provider that offer the Price.")
  private Provider provider;

  @JsonView(Views.Search.class)
  @Column(nullable = false)
  @Schema(description = "The price.")
  private int price;

  @JsonView(Views.Search.class)
  @Column(nullable = false)
  @Schema(description = "The currency of the price.")
  private String currency;

  /**
   * Default JPA constructor.
   */
  public Price() {
  }

  /**
   * Construct a new Price entity with the Provider and price.
   *
   * @param provider The provider object of the Price.
   * @param price    The price.
   */
  public Price(Provider provider, int price, String currency) {
    setProvider(provider);
    setPrice(price);
    setCurrency(currency);
  }

  /**
   * Return the unique identifier of the Price.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the Provider of the Price.
   *
   * @return The Provider of the entity.
   */
  public Provider getProvider() {
    return provider;
  }

  /**
   * Return the price of the Price.
   *
   * @return The price of the entity.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Return the currency of the Price.
   *
   * @return The currency of the entity.
   */
  public String getCurrency() {
    return currency;
  }


  /**
   * Sets the unique identifier for this Price.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than 0.
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("ID cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the Provider for this Price.
   *
   * @param provider The new Provider for this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if Provider is null.
   */
  public void setProvider(Provider provider) {
    if (provider == null) {
      throw new IllegalArgumentException("Provider cannot be null");
    }
    this.provider = provider;
  }

  /**
   * Sets the new price for this Price.
   *
   * @param price The new price for this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if price is less than zero.
   */
  public void setPrice(int price) {
    if (price < 0) {
      throw new IllegalArgumentException("Price be less then zero");
    }
    this.price = price;
  }

  /**
   * Sets the new price for this Price.
   *
   * @param currency The new price for this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if currency is null or empty.
   */
  public void setCurrency(String currency) {
    if (currency == null) {
      throw new IllegalArgumentException("Currency cannot be null");
    }
    if (currency.isEmpty() || currency.isBlank()) {
      throw new IllegalArgumentException("Currency cannot be blank");
    }
    this.currency = currency;
  }

  /**
   * Checks if the object is a valid Price.
   *
   * @return Return true if Price is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid() {
    boolean isValid;
    if (price < 0) {
      isValid = false;
    } else if (provider == null) {
      isValid = false;
    } else if (currency == null || currency.isEmpty() || currency.isBlank()) {
      isValid = false;
    } else {
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Price) obj;
    return this.id == that.id &&
        Objects.equals(this.provider, that.provider) &&
        Objects.equals(this.currency, that.currency) &&
        Objects.equals(this.price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, provider, price, currency);
  }

  @Override
  public String toString() {
    return "Price[" +
        "id=" + id + ", " +
        "Provider=" + provider + ", " +
        "Currency=" + currency + ", " +
        "price=" + price + ']';
  }

}
