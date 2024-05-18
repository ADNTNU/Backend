package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExtraFeatureTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testExtraFeatureValidId(){
    ExtraFeature extraFeature = new ExtraFeature();
    int id = 5;
    extraFeature.setId(id);
    assertEquals(extraFeature.getId(), id);
  }
  @Test
  void testExtraFeatureValidDescription(){
    ExtraFeature extraFeature = new ExtraFeature();
    String description = "description";
    extraFeature.setDescription(description);
    assertEquals(extraFeature.getDescription(), description);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testExtraFeatureInvalidId(){
    int testInt = -2;
    ExtraFeature extraFeature = new ExtraFeature();
    assertThrows(IllegalArgumentException.class, () -> extraFeature.setId(testInt));
  }
  @Test
  void testExtraFeatureInvalidDescription(){
    ExtraFeature extraFeature = new ExtraFeature();
    assertThrows(IllegalArgumentException.class, () -> extraFeature.setDescription(null));
    assertThrows(IllegalArgumentException.class, () -> extraFeature.setDescription(""));
    assertThrows(IllegalArgumentException.class, () -> extraFeature.setDescription("    "));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testExtraFeatureIsValidMethod(){
    ExtraFeature extraFeature = new ExtraFeature("description");
    assertEquals(true, extraFeature.isValid());
  }
  @Test
  void testExtraFeatureInvalidIsValidMethod(){
    ExtraFeature extraFeature = new ExtraFeature();
    assertEquals(false, extraFeature.isValid());
  }
}
