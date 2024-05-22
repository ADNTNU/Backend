package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlightTest {

  Location location = new Location("code", "name", "image");
  Airport airport = new Airport("code", "name", location);
  Airline airline = new Airline("name");

  /**
   * Testing all valid parameters
   */
  @Test
  void testFlightValidId(){
    Flight flight = new Flight();
    int id = 5;
    flight.setId(id);
    assertEquals(flight.getId(), id);
  }
  @Test
  void testFlightValidName(){
    Flight flight = new Flight();
    String name = "name";
    flight.setName(name);
    assertEquals(flight.getName(), name);
  }
  @Test
  void testFlightValidDepartureAirportId(){
    Flight flight = new Flight();
    flight.setDepartureAirport(airport);
    assertEquals(flight.getDepartureAirport(), airport);
  }
  @Test
  void testFlightValidArrivalAirportId(){
    Flight flight = new Flight();
    flight.setArrivalAirport(airport);
    assertEquals(flight.getArrivalAirport(), airport);
  }
  @Test
  void testFlightValidAirlineId(){
    Flight flight = new Flight();
    flight.setAirline(airline);
    assertEquals(flight.getAirline(), airline);
  }
  @Test
  void testFlightValidDepartureDate(){
    Flight flight = new Flight();
    LocalDateTime localDateTime = LocalDateTime.now();
    flight.setDepartureDate(localDateTime);
    assertEquals(flight.getDepartureDate(), localDateTime);
  }
  @Test
  void testFlightValidArrivalTime(){
    Flight flight = new Flight();
    LocalDateTime localDateTime = LocalDateTime.now();
    flight.setArrivalDate(localDateTime);
    assertEquals(flight.getArrivalDate(), localDateTime);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testFlightInvalidId(){
    int testInt = -2;
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setId(testInt));
  }
  @Test
  void testFlightInvalidName(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setName(null));
    assertThrows(IllegalArgumentException.class, () -> flight.setName(""));
    assertThrows(IllegalArgumentException.class, () -> flight.setName("    "));
  }
  @Test
  void testFlightInvalidDepartureAirportId(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setDepartureAirport(null));
  }
  @Test
  void testFlightInvalidArrivalAirportId(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setArrivalAirport(null));
  }
  @Test
  void testFlightInvalidAirlineId(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setAirline(null));
  }
  @Test
  void testFlightInvalidDepartureDate(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setDepartureDate(null));
  }
  @Test
  void testFlightInvalidArrivalTime(){
    Flight flight = new Flight();
    assertThrows(IllegalArgumentException.class, () -> flight.setArrivalDate(null));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testFlightIsValidMethod(){
    LocalDateTime localDateTime = LocalDateTime.now();
    Flight flight = new Flight("Name", airport, airport, airline, localDateTime, localDateTime);
    assertEquals(true, flight.isValid());
  }
  @Test
  void testFlightInvalidIsValidMethod(){
    Flight flight = new Flight();
    assertEquals(false, flight.isValid());
  }


}
