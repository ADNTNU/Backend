package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassTypeTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testClassTypeValidId(){
    ClassType classType = new ClassType();
    int id = 5;
    classType.setId(id);
    assertEquals(classType.getId(), id);
  }
  @Test
  void testClassTypeValidDescription(){
    ClassType classType = new ClassType();
    String description = "Description for classType";
    classType.setDescription(description);
    assertEquals(classType.getDescription(), description);
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testClassTypeInvalidId(){
    int testInt = -2;
    ClassType classType = new ClassType();
    assertThrows(IllegalArgumentException.class, () -> classType.setId(testInt));
  }
  @Test
  void testClassTypeInvalidDescription(){
    ClassType classType = new ClassType();
    assertThrows(IllegalArgumentException.class, () -> classType.setDescription(null));
    assertThrows(IllegalArgumentException.class, () -> classType.setDescription(""));
    assertThrows(IllegalArgumentException.class, () -> classType.setDescription("    "));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testClassTypeIsValidMethod(){
    ClassType classType = new ClassType("Description of class");
    assertEquals(true, classType.isValid());
  }
  @Test
  void testClassTypeInvalidIsValidMethod(){
    ClassType classType = new ClassType();
    assertEquals(false, classType.isValid());
  }

}
