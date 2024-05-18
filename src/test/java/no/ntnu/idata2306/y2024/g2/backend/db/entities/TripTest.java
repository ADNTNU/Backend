package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

  Location location = new Location("country", "name", "image");
  Airport airport = new Airport("Code", "Name", location);
  Airline airline = new Airline("Name");
  Flight flight = new Flight("Name", airport, airport, airline, new Date(), new Date());

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

}
