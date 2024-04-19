package no.ntnu.idata2306.y2024.g2.backend;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  private final Logger logger = LoggerFactory.getLogger("DummyInit");

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){
    Optional<User> existingUser = userRepository.findUserByEmail("danielneset@gmail.com");
    if(existingUser.isEmpty()){
      logger.info("Importing test data... ");

      User user = new User("Daniel", "Neset", "danielneset@gmail.com", createHash("Daniel1234!"));
      Role userRole = new Role("ROLE_USER");
      Role adminRole = new Role("ROLE_ADMIN");
      user.addRole(userRole);

      roleRepository.save(userRole);
      roleRepository.save(adminRole);
      userRepository.save(user);

      logger.info("Done importing test data");
    }else{
      logger.info("User already exists!");
    }
  }



  private String createHash(String password){
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

}
