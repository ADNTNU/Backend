package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.y2024.g2.backend.Views;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Views.IdOnly.class)
  private int id;
  @JsonView(Views.Full.class)
  private String firstName;
  @JsonView(Views.Full.class)
  private String lastName;
  @JsonView(Views.Full.class)
  private String email;
  @JsonView(Views.Full.class)
  private String password;
  @JsonView(Views.Full.class)
  private boolean active = true;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  @JsonView(Views.Full.class)
  private Set<Role> roles = new LinkedHashSet<>();

  public User(){
  }

  public User(String firstName, String lastName, String email, String password){
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPassword(password);
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setFirstName(String firstName) {
    if(firstName == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(firstName.isEmpty() || firstName.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    if(lastName == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(lastName.isEmpty() || lastName.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    if(email == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(email.isEmpty() || email.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.email = email;
  }

  public void setPassword(String password) {
    if(email == null){
      throw new IllegalArgumentException("Name cannot be null");
    }
    if(email.isEmpty() || email.isBlank()){
      throw new IllegalArgumentException("Name cannot be blank");
    }
    this.password = password;
  }

  public void addRole(Role role) {
    this.roles.add(role);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @JsonIgnore
  public boolean isValid(){
    return !firstName.isBlank() && !lastName.isBlank();
  }
}
