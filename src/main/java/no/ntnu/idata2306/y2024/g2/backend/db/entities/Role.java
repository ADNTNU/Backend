package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Role entity with a unique identifier, name and users.
 * The entity is used to store information in the database.
 *
 * @author Daniel Neset
 * @version 17.05.2024
 */
@Entity
@Schema(description = "Represents a Role.")
public class Role {
  @Id
  @GeneratedValue
  @JsonView({Views.IdOnly.class, Views.Search.class})
  @Schema(description = "The unique identifier of the Role.")
  public int id;

  @JsonView(Views.Search.class)
  @Schema(description = "The name of the role.")
  public String name;

  @ManyToMany(mappedBy = "roles")
  @JsonView(Views.Search.class)
  @JsonIgnore
  private Set<User> users = new LinkedHashSet<>();

  /**
   * Default JPA constructor.
   */
  public Role(){}

  /**
   * Construct a new Role entity with a name.
   *
   * @param name The name of the Role.
   */
  public Role(String name){
    setName(name);
  }

  /**
   * Return the unique identifier of the Role.
   *
   * @return The id of the entity.
   */
  public int getId() {
    return id;
  }

  /**
   * Return the name of the Role.
   *
   * @return Return the name of the entity.
   */
  public String getName() {
    return name;
  }

  /**
   * Return a list og user based on Role.
   *
   * @return Return a list of users.
   */
  public Set<User> getUsers() {
    return users;
  }

  /**
   * Sets the unique identifier for this Role.
   *
   * @param id The new id of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if id is less than 0.
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("ID cannot be less then zero");
    }
    this.id = id;
  }

  /**
   * Sets the new name of the Role.
   *
   * @param name The new name of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if name is null or empty.
   */
  public void setName(String name) {
    if(name == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(name.isEmpty() || name.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.name = name;
  }

  /**
   * Add a new user to the Role.
   *
   * @param user The new User of this entity.
   * @throws IllegalArgumentException Throws IllegalArgumentException if user is null.
   */
  public void addUsers(User user) {
    if(user == null){
      throw new IllegalArgumentException("User cannot be null");
    }
    this.users.add(user);
  }

  /**
   * Checks if the object is a valid Role.
   *
   * @return Return true if Role is valid. false otherwise.
   */
  @JsonIgnore
  public boolean isValid(){
    boolean isValid = false;
    if(name == null || name.isEmpty() || name.isBlank()){
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
    var that = (Role) obj;
    return this.id == that.id &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.users, that.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, users);
  }

  @Override
  public String toString() {
    return "Role[" +
            "id=" + id + ", " +
            "name=" + name + ", " +
            "users=" + users + ']';
  }
}
