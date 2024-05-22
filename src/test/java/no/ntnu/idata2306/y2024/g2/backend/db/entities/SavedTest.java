package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SavedTest {

  Location location = new Location("country", "name", "image");
  Airport airport = new Airport("Code", "Name", location);
  Airline airline = new Airline("Name");
  Flight flight = new Flight("Name", airport, airport, airline, LocalDateTime.now(), LocalDateTime.now());
  Provider provider = new Provider("Provider");
  Price price = new Price(provider, 1000, "NOK");
  ClassType classType = new ClassType("Name");
  ExtraFeature extraFeature = new ExtraFeature("description");

  /**
   * Test for all valid parameters.
   */
  @Test
  void testSavedValidId(){
    int testInt = 2;
    Saved saved = new Saved();
    saved.setId(testInt);
    assertEquals(saved.getId(), testInt);
  }
  @Test
  void testSavedValidUser(){
    User user = new User("Daniel", "Neset", "dn@at.no", "Dadabobo:)");
    Saved saved = new Saved();
    saved.setUser(user);
    assertEquals(saved.getUser(), user);
  }
  @Test
  void testSavedValidTrip(){
    Set<Price> prices = new HashSet<>();
    prices.add(price);
    Set<ClassType> classTypes = new HashSet<>();
    classTypes.add(classType);
    Set<ExtraFeature> extraFeatures = new HashSet<>();
    extraFeatures.add(extraFeature);
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);

    Trip trip = new Trip(flight, flight, flight, flight, prices, classTypes, extraFeatures, flights, flights);
    Saved saved = new Saved();
    saved.setTrip(trip);
    assertEquals(saved.getTrip(), trip);
  }
  @Test
  void testSavedValidSavedDate(){
    LocalDateTime localDateTime = LocalDateTime.now();
    Saved saved = new Saved();
    saved.setSavedDate(localDateTime);
    assertEquals(saved.getSavedDate(), localDateTime);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testSavedInvalidId(){
    int testInt = -2;
    Saved saved = new Saved();
    assertThrows(IllegalArgumentException.class, () -> saved.setId(testInt));
  }
  @Test
  void testSavedInvalidUser(){
    Saved saved = new Saved();
    assertThrows(IllegalArgumentException.class, () -> saved.setUser(null));
  }
  @Test
  void testSavedInvalidTrip(){
    Saved saved = new Saved();
    assertThrows(IllegalArgumentException.class, () -> saved.setTrip(null));
  }
  @Test
  void testSavedInvalidSavedDate(){
    Saved saved = new Saved();
    assertThrows(IllegalArgumentException.class, () -> saved.setSavedDate(null));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testSavedIsValidMethod(){
    Set<Price> prices = new HashSet<>();
    prices.add(price);
    Set<ClassType> classTypes = new HashSet<>();
    classTypes.add(classType);
    Set<ExtraFeature> extraFeatures = new HashSet<>();
    extraFeatures.add(extraFeature);
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);

    Trip trip = new Trip(flight, flight, flight, flight, prices, classTypes, extraFeatures, flights, flights);
    User user = new User("Daniel", "Neset", "dn@at.no", "Dadabobo:)");
    LocalDateTime localDateTime = LocalDateTime.now();
    Saved saved = new Saved(user, trip, localDateTime);

    assertTrue(saved.isValid());
  }
  @Test
  void testSavedInvalidIsValidMethod(){
    Saved saved = new Saved();
    assertFalse(saved.isValid());
  }


}
