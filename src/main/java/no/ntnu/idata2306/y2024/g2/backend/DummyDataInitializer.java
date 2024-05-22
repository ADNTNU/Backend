package no.ntnu.idata2306.y2024.g2.backend;

import no.ntnu.idata2306.y2024.g2.backend.db.builders.TripBuilder;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.*;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.UserRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final LocationService locationService;
  private final AirportService airportService;
  private final AirlineService airlineService;
  private final FlightService flightService;
  private final ProviderService providerService;
  private final PriceService priceService;
  private final ClassTypeService classTypeService;
  private final ExtraFeatureService extraFeatureService;
  private final TripService tripService;


  private final Logger logger = LoggerFactory.getLogger("DummyInit");

  @Autowired
  public DummyDataInitializer(UserRepository userRepository, RoleRepository roleRepository, LocationService locationService, AirportService airportService, AirlineService airlineService, FlightService flightService, ProviderService providerService, PriceService priceService, ClassTypeService classTypeService, ExtraFeatureService extraFeatureService, TripService tripService) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.locationService = locationService;
    this.airportService = airportService;
    this.airlineService = airlineService;
    this.flightService = flightService;
    this.providerService = providerService;
    this.priceService = priceService;
    this.classTypeService = classTypeService;
    this.extraFeatureService = extraFeatureService;
    this.tripService = tripService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    Optional<User> existingUser = userRepository.findUserByEmail("danielneset@gmail.com");
    Optional<Trip> existingTrip = tripService.getTrip(1);
    logger.info("Importing test data... ");

    if (existingUser.isEmpty()) {

      User user = new User("Daniel", "Neset", "danielneset@gmail.com", createHash("Daniel1234!"));
      Role userRole = new Role("ROLE_USER");
      Role adminRole = new Role("ROLE_ADMIN");
      user.addRole(adminRole);
      user.addRole(userRole);

      User user2 = new User("Anders", "Lund",  "a_lund_01@hotmail.com", createHash("BMY6ckz!mye-jtk3rnr"));
      user2.addRole(userRole);
      user2.addRole(adminRole);

      roleRepository.save(userRole);
      roleRepository.save(adminRole);
      userRepository.save(user);
      userRepository.save(user2);
    } else {
      logger.info("User already exists!");
    }

    if (existingTrip.isEmpty()) {
      Location location1 = new Location("Norway", "Oslo", null);
      Location location2 = new Location("USA", "Los Angeles", "https://source.unsplash.com/400x400/?los-angeles");
      Location location3 = new Location("USA", "New York", "https://source.unsplash.com/400x400/?new-york");

      Airport airport1 = new Airport("OSL", "Oslo lufthavn", location1);
      Airport airport2 = new Airport("LAX", "Los Angeles International Airport", location2);
      Airport airport3 = new Airport("JFK", "John F. Kennedy International Airport", location3);

      Airline airline = new Airline("SAS");

      Flight flight1 = new Flight("Flight 1", airport1, airport2, airline, LocalDateTime.now(), LocalDateTime.now().plusHours(10));
      Flight flight2 = new Flight("Flight 2", airport2, airport1, airline, LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(22));
      Flight flight3 = new Flight("Flight 3", airport1, airport3, airline, LocalDateTime.now(), LocalDateTime.now().plusHours(6));
      Flight flight4 = new Flight("Flight 4", airport3, airport2, airline, LocalDateTime.now().plusHours(8), LocalDateTime.now().plusHours(13));

      Provider provider1 = new Provider("Google");
      Provider provider2 = new Provider("Expedia");

      Price price1 = new Price(provider1, 1000, "NOK");
      Price price2 = new Price(provider2, 2000, "NOK");
      Set<Price> prices = Set.of(price1, price2);

      ClassType classType1 = new ClassType("Economy");
      ClassType classType2 = new ClassType("Business");
      Set<ClassType> classTypes = Set.of(classType1, classType2);

      ExtraFeature extraFeature1 = new ExtraFeature("Wifi");
      ExtraFeature extraFeature2 = new ExtraFeature("Food");
      Set<ExtraFeature> extraFeatures = Set.of(extraFeature1, extraFeature2);

      Trip trip = new TripBuilder()
          .setLeaveInitialFlight(flight1)
          .setReturnInitialFlight(flight2)
          .setPrices(prices)
          .setClassTypes(classTypes)
          .setExtraFeatures(extraFeatures)
          .build();
      Trip trip2 = new TripBuilder()
          .setLeaveInitialFlight(flight3)
          .setLeaveArrivalFlight(flight4)
          .setReturnInitialFlight(flight2)
          .setPrices(prices)
          .setClassTypes(classTypes)
          .setExtraFeatures(extraFeatures)
          .build();
      Trip trip3 = new TripBuilder()
          .setLeaveInitialFlight(flight1)
          .setPrices(prices)
          .setClassTypes(classTypes)
          .setExtraFeatures(extraFeatures)
          .build();

      locationService.addLocation(location1);
      locationService.addLocation(location2);
      locationService.addLocation(location3);

      airportService.addAirport(airport1);
      airportService.addAirport(airport2);
      airportService.addAirport(airport3);

      airlineService.addAirline(airline);
      flightService.addFlight(flight1);
      flightService.addFlight(flight2);
      flightService.addFlight(flight3);
      flightService.addFlight(flight4);

      providerService.addProvider(provider1);
      providerService.addProvider(provider2);
      priceService.addPrice(price1);
      priceService.addPrice(price2);
      classTypeService.addClassType(classType1);
      classTypeService.addClassType(classType2);
      extraFeatureService.addExtraFeature(extraFeature1);
      extraFeatureService.addExtraFeature(extraFeature2);
      tripService.addTrip(trip);
      tripService.addTrip(trip2);
      tripService.addTrip(trip3);
    } else {
      logger.info("Trip already exists!");
    }
    logger.info("Done importing test data");

  }


  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

}
