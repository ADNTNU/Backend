package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirlineTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testAirlineValidId(){
    Airline airline = new Airline();
    int id = 5;
    airline.setId(id);
    assertEquals(airline.getId(), id);
  }

  @Test
  void testAirlineValidName(){
    Airline airline = new Airline();
    String name = "name";
    airline.setName(name);
    assertEquals(airline.getName(), name);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testAirlineInvalidId(){
    int testInt = -2;
    Airline airline = new Airline();
    assertThrows(IllegalArgumentException.class, () -> airline.setId(testInt));
  }

  @Test
  void testAirlineInvalidName(){
    Airline airline = new Airline();
    assertThrows(IllegalArgumentException.class, () -> airline.setName(null));
    assertThrows(IllegalArgumentException.class, () -> airline.setName(""));
    assertThrows(IllegalArgumentException.class, () -> airline.setName("    "));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testAirlineIsValidMethod(){
    Airline airline = new Airline("name");
    assertEquals(true, airline.isValid());
  }
  @Test
  void testAirlineInvalidIsValidMethod(){
    Airline airline = new Airline();
    assertEquals(false, airline.isValid());
  }

}
