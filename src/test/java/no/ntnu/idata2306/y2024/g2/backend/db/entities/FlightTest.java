package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    flight.setDepartureAirportId(airport);
    assertEquals(flight.getDepartureAirportId(), airport);
  }
  @Test
  void testFlightValidArrivalAirportId(){
    Flight flight = new Flight();
    flight.setArrivalAirportId(airport);
    assertEquals(flight.getArrivalAirportId(), airport);
  }
  @Test
  void testFlightValidAirlineId(){
    Flight flight = new Flight();
    flight.setAirlineId(airline);
    assertEquals(flight.getAirlineId(), airline);
  }
  @Test
  void testFlightValidDepartureDate(){
    Flight flight = new Flight();
    Date date = parseDate("2024-05-17");
    flight.setDepartureDate(date);
    assertEquals(flight.getDepartureDate(), date);
  }
  @Test
  void testFlightValidArrivalTime(){
    Flight flight = new Flight();
    Date date = parseDate("2024-05-17");
    flight.setArrivalTime(date);
    assertEquals(flight.getArrivalTime(), date);
  }



  private Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      return null;
    }
  }

}
