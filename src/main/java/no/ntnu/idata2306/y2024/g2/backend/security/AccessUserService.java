package no.ntnu.idata2306.y2024.g2.backend.security;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AccessUserService implements UserDetailsService {

  private static final int MIN_PASSWORD_LENGTH = 8;
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    Optional<User> user = userRepository.findUserByEmail(username);
    if(user.isPresent()) {
      return new AccessUserDetails(user.get());
    }else {
      throw new UsernameNotFoundException("USer: " + username + " not found!");
    }
  }

  public User getSessionUser(){
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = authentication.getName();
    return userRepository.findUserByEmail(username).orElse(null);
  }

  private boolean userExists(String username){
    try{
      loadUserByUsername(username);
      return true;
    }catch (UsernameNotFoundException usernameNotFoundException){
      return false;
    }
  }

  public void tryCreateNewUser(String username, String password) throws IOException {
    String errorMessage;
    if("".equals(username)) {
      errorMessage = "Username cant be empty";
    }else if (userExists(username)){
      errorMessage = "Username already exists";
    }else {
      errorMessage = checkPasswordRequirements(password);
      if(errorMessage == null){
        createUser(username, password);
      }
    }
    if(errorMessage != null){
      throw new IOException(errorMessage);
    }
  }

  private String checkPasswordRequirements(String password){
    String errorMessage = null;
    if(password == null || password.length() == 0){
      errorMessage = "Password cannot be empty";
    }else if(password.length() < MIN_PASSWORD_LENGTH){
      errorMessage = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
    }
    return errorMessage;
  }

  private void createUser(String username, String password){
    Role userRole = roleRepository.findOneByName("ROLE_USER");
    if(userRole != null){
      User user = new User("firstName", "LastName", username, createHash(password));
      user.addRole(userRole);
      userRepository.save(user);
    }
  }

  private String createHash(String password){
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

}
