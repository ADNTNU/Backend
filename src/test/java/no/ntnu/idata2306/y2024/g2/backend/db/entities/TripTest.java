package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {

  Location location = new Location("country", "name", "image");
  Airport airport = new Airport("Code", "Name", location);
  Airline airline = new Airline("Name");
  Flight flight = new Flight("Name", airport, airport, airline, LocalDateTime.now(), LocalDateTime.now());
  Provider provider = new Provider("Provider");
  Price price = new Price(provider, 1000, "NOK");
  ClassType classType = new ClassType("Name");
  ExtraFeature extraFeature = new ExtraFeature("description");

  /**
   * Testing all valid parameters
   */
  @Test
  void testTripValidId(){
    Trip trip = new Trip();
    int id = 5;
    trip.setId(id);
    assertEquals(trip.getId(), id);
  }
  @Test
  void testTripValidLeaveInitialFlightId(){
    Trip trip = new Trip();
    trip.setLeaveInitialFlight(flight);
    assertEquals(trip.getLeaveInitialFlight(), flight);
  }
  @Test
  void testTripValidLeaveArrivalFlightId(){
    Trip trip = new Trip();
    trip.setLeaveArrivalFlight(flight);
    assertEquals(trip.getLeaveArrivalFlight(), flight);
  }
  @Test
  void testTripValidReturnArrivalFlight(){
    Trip trip = new Trip();
    trip.setReturnArrivalFlight(flight);
    assertEquals(trip.getReturnArrivalFlight(), flight);
  }
  @Test
  void testTripValidReturnInitialFlight(){
    Trip trip = new Trip();
    trip.setReturnInitialFlight(flight);
    assertEquals(trip.getReturnInitialFlight(), flight);
  }
  @Test
  void testTripValidPrices(){
    Trip trip = new Trip();
    Set<Price> prices = new HashSet<>();
    prices.add(price);
    trip.setPrices(prices);
    assertEquals(trip.getPrices(), prices);
  }
  @Test
  void testTripValidClassTypes(){
    Trip trip = new Trip();
    Set<ClassType> classTypes = new HashSet<>();
    classTypes.add(classType);
    trip.setClassTypes(classTypes);
    assertEquals(trip.getClassTypes(), classTypes);
  }
  @Test
  void testTripValidExtraFeatures(){
    Trip trip = new Trip();
    Set<ExtraFeature> extraFeatures = new HashSet<>();
    extraFeatures.add(extraFeature);
    trip.setExtraFeatures(extraFeatures);
    assertEquals(trip.getExtraFeatures(), extraFeatures);
  }
  @Test
  void testTripValidLeaveFlightIntervals(){
    Trip trip = new Trip();
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);
    trip.setLeaveArrivalFlight(flight);
    trip.setLeaveFlightIntervals(flights);
    assertEquals(trip.getLeaveFlightIntervals(), flights);
  }
  @Test
  void testTripValidReturnFlightIntervals(){
    Trip trip = new Trip();
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);
    trip.setReturnArrivalFlight(flight);
    trip.setReturnFlightIntervals(flights);
    assertEquals(trip.getReturnFlightIntervals(), flights);
  }

  @Test
  void testTripValidActive(){
    Trip trip = new Trip();
    trip.setActive(false);
    assertFalse(trip.isActive());
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testTripInvalidId(){
    int testInt = -2;
    Trip trip = new Trip();
    assertThrows(IllegalArgumentException.class, () -> trip.setId(testInt));
  }
  @Test
  void testTripInvalidLeaveInitialFlightId(){
    Trip trip = new Trip();
    assertThrows(IllegalArgumentException.class, () -> trip.setLeaveInitialFlight(null));
  }
  @Test
  void testTripInvalidPrices(){
    Trip trip = new Trip();
    Set<Price> prices = new HashSet<>();
    assertThrows(IllegalArgumentException.class, () -> trip.setPrices(null));
    assertThrows(IllegalArgumentException.class, () -> trip.setPrices(prices));
  }
  @Test
  void testTripInvalidClassTypes(){
    Trip trip = new Trip();
    Set<ClassType> classTypes = new HashSet<>();
    assertThrows(IllegalArgumentException.class, () -> trip.setClassTypes(null));
    assertThrows(IllegalArgumentException.class, () -> trip.setClassTypes(classTypes));
  }
  @Test
  void testTripInvalidExtraFeatures(){
    Trip trip = new Trip();
    Set<ExtraFeature> extraFeatures = new HashSet<>();
    assertThrows(IllegalArgumentException.class, () -> trip.setExtraFeatures(null));
    assertThrows(IllegalArgumentException.class, () -> trip.setExtraFeatures(extraFeatures));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testTripIsValidMethod(){
    Set<Price> prices = new HashSet<>();
    prices.add(price);
    Set<ClassType> classTypes = new HashSet<>();
    classTypes.add(classType);
    Set<ExtraFeature> extraFeatures = new HashSet<>();
    extraFeatures.add(extraFeature);
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);

    Trip trip = new Trip(flight, flight, flight, flight, prices, classTypes, extraFeatures, flights, flights);
    assertEquals(true, trip.isValid());
  }
  @Test
  void testTripInvalidIsValidMethod(){
    Trip trip = new Trip();
    assertEquals(false, trip.isValid());
  }


}
