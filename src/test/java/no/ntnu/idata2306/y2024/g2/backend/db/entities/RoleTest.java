package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleTest {

  /**
   * Test for all valid parameters.
   */
  @Test
  void testRoleValidId(){
    int testInt = 2;
    Role role = new Role();
    role.setId(testInt);
    assertEquals(role.getId(), testInt);
  }
  @Test
  void testRoleValidName(){
    String name = "name";
    Role role = new Role();
    role.setName(name);
    assertEquals(role.getName(), name);
  }
  @Test
  void testRoleValidUsers(){
    Set<User> users = new HashSet<>();
    User user = new User("first", "last", "d@n.o", "wapapapapa!");
    users.add(user);
    Role role = new Role();
    role.addUsers(user);
    assertTrue(role.getUsers().contains(user));
  }


  /**
   * Testing invalid parameters
   */
  @Test
  void testRoleInvalidId(){
    int testInt = -2;
    Role role = new Role();
    assertThrows(IllegalArgumentException.class, () -> role.setId(testInt));
  }
  @Test
  void testRoleInvalidName(){
    Role role = new Role();
    assertThrows(IllegalArgumentException.class, () -> role.setName(null));
    assertThrows(IllegalArgumentException.class, () -> role.setName(""));
    assertThrows(IllegalArgumentException.class, () -> role.setName("    "));
  }
  @Test
  void testRoleInvalidUser(){
    Role role = new Role();
    assertThrows(IllegalArgumentException.class, () -> role.addUsers(null));
  }

  /**
   * Test "isValid" method
   */
  @Test
  void testRoleIsValidMethod(){
    Role role = new Role("ROLE_ADMIN");
    assertEquals(true, role.isValid());
  }
  @Test
  void testRoleInvalidIsValidMethod(){
    Role role = new Role();
    assertEquals(false, role.isValid());
  }

}
