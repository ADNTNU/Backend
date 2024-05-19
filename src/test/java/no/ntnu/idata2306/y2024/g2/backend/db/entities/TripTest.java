package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {

  Location location = new Location("country", "name", "image");
  Airport airport = new Airport("Code", "Name", location);
  Airline airline = new Airline("Name");
  Flight flight = new Flight("Name", airport, airport, airline, new Date(), new Date());
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
    trip.setLeaveInitialFlightId(flight);
    assertEquals(trip.getLeaveInitialFlightId(), flight);
  }
  @Test
  void testTripValidLeaveArrivalFlightId(){
    Trip trip = new Trip();
    trip.setLeaveArrivalFlightId(flight);
    assertEquals(trip.getLeaveArrivalFlightId(), flight);
  }
  @Test
  void testTripValidReturnArrivalFlightId(){
    Trip trip = new Trip();
    trip.setReturnArrivalFlightId(flight);
    assertEquals(trip.getReturnArrivalFlightId(), flight);
  }
  @Test
  void testTripValidReturnInitialFlightId(){
    Trip trip = new Trip();
    trip.setReturnInitialFlightId(flight);
    assertEquals(trip.getReturnInitialFlightId(), flight);
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
  void testTripValidDepartureFlightInterval(){
    Trip trip = new Trip();
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);
    trip.setDepartureFlightInterval(flights);
    assertEquals(trip.getDepartureFlightInterval(), flights);
  }
  @Test
  void testTripValidReturnFlightIntervals(){
    Trip trip = new Trip();
    Set<Flight> flights = new HashSet<>();
    flights.add(flight);
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
    assertThrows(IllegalArgumentException.class, () -> trip.setLeaveInitialFlightId(null));
  }
  @Test
  void testTripInvalidLeaveArrivalFlightId(){
    Trip trip = new Trip();
    assertThrows(IllegalArgumentException.class, () -> trip.setLeaveArrivalFlightId(null));
  }
  @Test
  void testTripInvalidReturnArrivalFlightId(){
    Trip trip = new Trip();
    assertThrows(IllegalArgumentException.class, () -> trip.setReturnArrivalFlightId(null));
  }
  @Test
  void testTripInvalidReturnInitialFlightId(){
    Trip trip = new Trip();
    assertThrows(IllegalArgumentException.class, () -> trip.setReturnInitialFlightId(null));
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
  @Test
  void testTripInvalidDepartureFlightInterval(){
    Trip trip = new Trip();
    Set<Flight> departureFlightInterval = new HashSet<>();
    assertThrows(IllegalArgumentException.class, () -> trip.setDepartureFlightInterval(null));
    assertThrows(IllegalArgumentException.class, () -> trip.setDepartureFlightInterval(departureFlightInterval));
  }
  @Test
  void testTripInvalidReturnFlightIntervals(){
    Trip trip = new Trip();
    Set<Flight> returnFlightIntervals = new HashSet<>();
    assertThrows(IllegalArgumentException.class, () -> trip.setReturnFlightIntervals(null));
    assertThrows(IllegalArgumentException.class, () -> trip.setReturnFlightIntervals(returnFlightIntervals));
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
