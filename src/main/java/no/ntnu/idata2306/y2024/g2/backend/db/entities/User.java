package no.ntnu.idata2306.y2024.g2.backend.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private int permission;

  public User(){
  }

  public User(String firstName, String lastName, String email, String password, int permission){
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
    setPassword(password);
    setPermission(permission);
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

  public int getPermission() {
    return permission;
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

  public void setPermission(int permission) {
    this.permission = permission;
  }

  @JsonIgnore
  public boolean isValid(){
    return !firstName.isBlank() && !lastName.isBlank();
  }
}
