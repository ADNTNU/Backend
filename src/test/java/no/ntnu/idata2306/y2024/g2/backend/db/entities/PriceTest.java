package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testPriceValidId(){
    Price price = new Price();
    int id = 5;
    price.setId(id);
    assertEquals(price.getId(), id);
  }
  @Test
  void testPriceValidPrice(){
    Price price = new Price();
    int money = 2000;
    price.setPrice(money);
    assertEquals(price.getPrice(), money);
  }
  @Test
  void testPriceValidProvider(){
    Price price = new Price();
    Provider provider = new Provider("Google");
    price.setProvider(provider);
    assertEquals(price.getProvider(), provider);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testPriceInvalidId(){
    int testInt = -2;
    Price price = new Price();
    assertThrows(IllegalArgumentException.class, () -> price.setId(testInt));
  }
  @Test
  void testPriceInvalidPrice(){
    Price price = new Price();
    assertThrows(IllegalArgumentException.class, () -> price.setPrice(-10));
  }
  @Test
  void testPriceInvalidProvider(){
    Price price = new Price();
    assertThrows(IllegalArgumentException.class, () -> price.setProvider(null));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testPriceIsValidMethod(){
    Price price = new Price(new Provider("google"), 20);
    assertEquals(true, price.isValid());
  }
  @Test
  void testPriceInvalidIsValidMethod(){
    Price price = new Price();
    assertEquals(false, price.isValid());
  }


}
