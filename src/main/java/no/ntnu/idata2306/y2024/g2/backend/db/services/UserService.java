package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers(){
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  public Optional<User> getUserById(int id){
    return userRepository.findById(id);
  }

  public void addUser(User user){
    userRepository.save(user);
  }

  public void updateUser(User user){
    userRepository.save(user);
  }

  public void deleteUser(User user){
    userRepository.delete(user);
  }

  public void deleteUserById(int id){
    userRepository.deleteById(id);
  }

}
