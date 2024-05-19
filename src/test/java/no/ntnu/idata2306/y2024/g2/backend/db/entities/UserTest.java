package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

  /**
   * Testing all valid parameters
   */
  @Test
  void testUserValidId(){
    User user = new User();
    int id = 5;
    user.setId(id);
    assertEquals(user.getId(), id);
  }
  @Test
  void testUserValidFirstName(){
    User user = new User();
    String firstName = "firstName";
    user.setFirstName(firstName);
    assertEquals(user.getFirstName(), firstName);
  }
  @Test
  void testUserValidLastName(){
    User user = new User();
    String lastName = "lastName";
    user.setLastName(lastName);
    assertEquals(user.getLastName(), lastName);
  }
  @Test
  void testUserValidEmail(){
    User user = new User();
    String email = "email";
    user.setEmail(email);
    assertEquals(user.getEmail(), email);
  }
  @Test
  void testUserValidPassword(){
    User user = new User();
    String password = "password";
    user.setPassword(password);
    assertEquals(user.getPassword(), password);
  }
  @Test
  void testUserValidActive(){
    User user = new User();
    Boolean active = false;
    user.setActive(active);
    assertEquals(user.isActive(), active);
  }
  @Test
  void testUserValidRoles(){
    User user = new User();
    Role role = new Role("ROLE_ADMIN");
    user.addRole(role);
    assertTrue(user.getRoles().contains(role));
  }

  /**
   * Testing invalid parameters
   */
  @Test
  void testUserInvalidId(){
    int testInt = -2;
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.setId(testInt));
  }
  @Test
  void testUserInvalidFirstName(){
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.setFirstName(null));
    assertThrows(IllegalArgumentException.class, () -> user.setFirstName(""));
    assertThrows(IllegalArgumentException.class, () -> user.setFirstName("    "));
  }
  @Test
  void testUserInvalidLastName(){
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.setLastName(null));
    assertThrows(IllegalArgumentException.class, () -> user.setLastName(""));
    assertThrows(IllegalArgumentException.class, () -> user.setLastName("    "));
  }
  @Test
  void testUserInvalidEmail(){
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail(""));
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("    "));
  }
  @Test
  void testUserInvalidPassword(){
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.setPassword(null));
    assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
    assertThrows(IllegalArgumentException.class, () -> user.setPassword("    "));
  }
  @Test
  void testUserInvalidRole(){
    User user = new User();
    assertThrows(IllegalArgumentException.class, () -> user.addRole(null));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testUserIsValidMethod(){
    User user = new User("first", "last", "d@n.d", "pass", new Role("admin"));
    assertEquals(true, user.isValid());
  }
  @Test
  void testUserInvalidIsValidMethod(){
    User user = new User();
    assertEquals(false, user.isValid());
  }
}
