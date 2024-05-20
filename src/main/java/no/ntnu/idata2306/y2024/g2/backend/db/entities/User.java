package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a User entity with a unique identifier, firstname, lastname
 * email, password, active and roles.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@Entity(name = "users")
@Schema(description = "Represents a User.")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier of the User.")
  @JsonView(Views.IdOnly.class)
  private int id;
  @Column(nullable = false)
  @Schema(description = "The first name of the user.")
  private String firstName;
  @Column(nullable = false)
  @Schema(description = "The last name of the user.")
  private String lastName;
  @Column(nullable = false)
  @Schema(description = "The email of the user.")
  private String email;
  @Column(nullable = false)
  @Schema(description = "The password of the user.")
  private String password;
  @Schema(description = "The active status of the user.")
  private boolean active = true;
  @ManyToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinTable(name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<>();

  /**
   * Default JPA constructor.
   */
  public User(){}

  /**
   * Construct a new User entity with the following parameters.
   *
   * @param firstName The firstName of the User.
   * @param lastName The lastName of the User.
   * @param email The email of the User.
   * @param password The password of the User.
   */
  public User(String firstName, String lastName, String email, String password){
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPassword(password);
  }

  /**
   * Construct a new User entity with the following parameters.
   *
   * @param firstName The firstName of the User.
   * @param lastName The lastName of the User.
   * @param email The email of the User.
   * @param password The password of the User.
   * @param role The role of the User.
   */
  public User(String firstName, String lastName, String email, String password, Role role){
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPassword(password);
    addRole(role);
  }

  /**
   * Return the unique identifier of the User.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the firstName of the User.
   *
   * @return Return the firstName of the entity.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Return the lastName of the User.
   *
   * @return Return the lastName of the entity.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Return the email of the User.
   *
   * @return Return the email of the entity.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Return the password of the User.
   *
   * @return Return the password of the entity.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Return the active status of the User.
   *
   * @return Return the active status of this entity.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Return the roles of the User.
   *
   * @return Return the roles of the entity.
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * Sets the unique identifier for this User.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than 0.
   */
  public void setId(int id){
    if (id < 0) {
      throw new IllegalArgumentException("ID cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the new first name of the User.
   *
   * @param firstName The new firs name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if firstname is null or empty.
   */
  public void setFirstName(String firstName) {
    if(firstName == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(firstName.isEmpty() || firstName.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.firstName = firstName;
  }

  /**
   * Sets the new last name of the User.
   *
   * @param lastName The new last name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if lastName is null or empty.
   */
  public void setLastName(String lastName) {
    if(lastName == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(lastName.isEmpty() || lastName.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.lastName = lastName;
  }

  /**
   * Sets the new email of the User.
   *
   * @param email The new email of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if email is null or empty.
   */
  public void setEmail(String email) {
    if(email == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(email.isEmpty() || email.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.email = email;
  }

  /**
   * Sets the new password of the User.
   *
   * @param password The new password of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if password is null or empty.
   */
  public void setPassword(String password) {
    if(password == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(password.isEmpty() || password.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.password = password;
  }

  /**
   * Sets the new roles of the User.
   *
   * @param role The new roles of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if roles is empty.
   */
  public void addRole(Role role) {
    if(role == null){
      throw new IllegalArgumentException("Role cannot be null");
    }
    this.roles.add(role);
  }

  /**
   * Sets the new active status of the User.
   *
   * @param active The new active status of this entity.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Checks if the object is a valid User.
   *
   * @return Return true if User is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if(firstName == null || firstName.isEmpty() || firstName.isBlank()){
      isValid = false;
    }else if (lastName == null || lastName.isEmpty() || lastName.isBlank()){
      isValid = false;
    }else if (email == null || email.isEmpty() || email.isBlank()){
      isValid = false;
    }else if (password == null || password.isEmpty() || password.isBlank()){
      isValid = false;
    }else if (roles.isEmpty()){
      isValid = false;
    }else{
      isValid = true;
    }
    return isValid;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (User) obj;
    return this.id == that.id &&
            Objects.equals(this.firstName, that.firstName) &&
            Objects.equals(this.lastName, that.lastName) &&
            Objects.equals(this.email, that.email) &&
            Objects.equals(this.password, that.password) &&
            Objects.equals(this.active, that.active) &&
            Objects.equals(this.roles, that.roles);
  }

  /**
   * Authentication do not work when this is in use
  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, password, active, roles);
  }*/

  @Override
  public String toString() {
    return "User[" +
            "id=" + id + ", " +
            "firstName=" + firstName + ", " +
            "lastName=" + lastName + ", " +
            "email=" + email + ", " +
            "password=" + password + ", " +
            "active=" + active + ", " +
            "roles=" + roles + ']';
  }
}
