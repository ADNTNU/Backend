package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProviderTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testProviderValidId(){
    Provider provider = new Provider();
    int id = 5;
    provider.setId(id);
    assertEquals(provider.getId(), id);
  }
  @Test
  void testProviderValidName(){
    Provider provider = new Provider();
    String name = "name";
    provider.setName(name);
    assertEquals(provider.getName(), name);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testProviderInvalidId(){
    int testInt = -2;
    Provider provider = new Provider();
    assertThrows(IllegalArgumentException.class, () -> provider.setId(testInt));
  }
  @Test
  void testProviderInvalidCode(){
    Provider provider = new Provider();
    assertThrows(IllegalArgumentException.class, () -> provider.setName(null));
    assertThrows(IllegalArgumentException.class, () -> provider.setName(""));
    assertThrows(IllegalArgumentException.class, () -> provider.setName("    "));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testProviderIsValidMethod(){
    Provider provider = new Provider("name");
    assertEquals(true, provider.isValid());
  }
  @Test
  void testProviderInvalidIsValidMethod(){
    Provider provider = new Provider();
    assertEquals(false, provider.isValid());
  }

}
