package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

  /**
   * Test for all valid parameters.
   */
  @Test
  void testLocationValidId(){
    int testInt = 2;
    Location location = new Location();
    location.setId(testInt);
    assertEquals(location.getId(), testInt);
  }

  @Test
  void testLocationValidName(){
    String testString = "Daniel";
    Location location = new Location();
    location.setName(testString);
    assertEquals(location.getName(), testString);
  }

  @Test
  void testLocationValidCountry(){
    String testString = "Norway";
    Location location = new Location();
    location.setCountry(testString);
    assertEquals(location.getCountry(), testString);
  }

  @Test
  void testLocationValidImnage(){
    String testString = "puppy";
    Location location = new Location();
    location.setImage(testString);
    assertEquals(location.getImage(), testString);
  }

  /**
   * Test for all invalid parameters
   */
  @Test
  void testLocationInvalidId(){
    int testInt = -2;
    Location location = new Location();
    assertThrows(IllegalArgumentException.class, () -> location.setId(testInt));
  }

  @Test
  void testLocationInvalidName(){
    Location location = new Location();
    assertThrows(IllegalArgumentException.class, () -> location.setName(null));
    assertThrows(IllegalArgumentException.class, () -> location.setName(""));
    assertThrows(IllegalArgumentException.class, () -> location.setName("    "));
  }

  @Test
  void testLocationInvalidCountry(){
    Location location = new Location();
    assertThrows(IllegalArgumentException.class, () -> location.setCountry(null));
    assertThrows(IllegalArgumentException.class, () -> location.setCountry(""));
    assertThrows(IllegalArgumentException.class, () -> location.setCountry("    "));
  }

  @Test
  void testLocationInvalidImage(){
    Location location = new Location();
    assertThrows(IllegalArgumentException.class, () -> location.setImage(""));
    assertThrows(IllegalArgumentException.class, () -> location.setImage("    "));
  }


  /**
   * Test "isValid" function
   */
  @Test
  void testLocationIsValid(){
    Location location = new Location("country", "name", "img");
    assertEquals(true, location.isValid());
  }

  @Test
  void testLocationIsValidInvalid(){
    Location location = new Location();
    assertEquals(false, location.isValid());
  }

}
