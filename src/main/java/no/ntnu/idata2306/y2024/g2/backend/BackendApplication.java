package no.ntnu.idata2306.y2024.g2.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot application.
 * This class serves as the entry point for the backend system, initializing and configuring the Spring context.
 * {@link SpringBootApplication} annotation denotes this as a Spring Boot application.
 *
 * @author Daniel Neset
 * @version 13.02.2024
 */
@SpringBootApplication
public class BackendApplication {

  /**
   * Main method to start up the Spring Boot application.
   *
   * @param args The command-line arguments passed during the start of the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

}
