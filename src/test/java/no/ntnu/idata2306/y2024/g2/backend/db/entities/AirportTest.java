package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirportTest {

  Location location = new Location("Country", "Name");

  /**
   * Testing all valid parameters
   */
  @Test
  void testAirportValidId(){
    Airport airport = new Airport();
    int id = 5;
    airport.setId(id);
    assertEquals(airport.getId(), id);
  }

  @Test
  void testAirportValidCode(){
    Airport airport = new Airport();
    String code = "code";
    airport.setCode(code);
    assertEquals(airport.getCode(), code);
  }

  @Test
  void testAirportValidName(){
    Airport airport = new Airport();
    String name = "name";
    airport.setName(name);
    assertEquals(airport.getName(), name);
  }

  @Test
  void testAirportValidLocation(){
    Airport airport = new Airport();
    Location location = this.location;
    airport.setLocation(location);
    assertEquals(airport.getLocation(), location);
  }

  /**
   * Test all parameters with invalid values
   */
  @Test
  void testAirportInvalidId(){
    int testInt = -2;
    Airport airport = new Airport();
    assertThrows(IllegalArgumentException.class, () -> airport.setId(testInt));
  }

  @Test
  void testAirportInvalidCode(){
    Airport airport = new Airport();
    assertThrows(IllegalArgumentException.class, () -> airport.setCode(null));
    assertThrows(IllegalArgumentException.class, () -> airport.setCode(""));
    assertThrows(IllegalArgumentException.class, () -> airport.setCode("    "));
  }

  @Test
  void testAirportInvalidName(){
    Airport airport = new Airport();
    assertThrows(IllegalArgumentException.class, () -> airport.setName(null));
    assertThrows(IllegalArgumentException.class, () -> airport.setName(""));
    assertThrows(IllegalArgumentException.class, () -> airport.setName("    "));
  }

  @Test
  void testAirportInvalidLocation(){
    Airport airport = new Airport();
    assertThrows(IllegalArgumentException.class, () -> airport.setLocation(null));
    assertThrows(IllegalArgumentException.class, () -> airport.setLocation(new Location()));
  }

  /**
   * Test "isvalid" method
   */
  @Test
  void testAirportIsValidMethod(){
    Airport airport = new Airport("code", "name", location);
    assertEquals(true, airport.isValid());
  }
  @Test
  void testAirportInvalidIsValidMethod(){
    Airport airport = new Airport();
    assertEquals(false, airport.isValid());
  }

}
