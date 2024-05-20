package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for {@link Trip} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations for the management of Trip entities.
 * This interface can be extended to include more complex queries specific to the Trip entity as needed.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface TripRepository extends JpaRepository<Trip, Integer> {
  @Query("SELECT t FROM Trip t LEFT JOIN t.leaveArrivalFlight laf JOIN t.leaveInitialFlight lif WHERE lif.departureAirport.id IN :departureAirportIds " +
      "AND ((laf IS NOT NULL AND laf.arrivalAirport.id IN :arrivalAirportIds) OR (laf IS NULL AND lif.arrivalAirport.id IN :arrivalAirportIds))" +
      "AND t.returnInitialFlight IS NULL " +
      "AND t.leaveInitialFlight.departureDate BETWEEN :departureDateLower AND :departureDateUpper " +
      "ORDER BY t.leaveInitialFlight.departureDate ASC ")
  List<Trip> findOneWayTripsByAirportIdsAndDepartureDate(@Param("departureAirportIds") List<Integer> departureAirportIds,
                                                         @Param("departureDateLower") LocalDateTime departureDateLower,
                                                         @Param("departureDateUpper") LocalDateTime departureDateUpper,
                                                         @Param("arrivalAirportIds") List<Integer> arrivalAirportIds,
                                                          Pageable pageable);
  @Query("SELECT t FROM Trip t " +
      "LEFT JOIN t.leaveArrivalFlight laf " +
      "JOIN t.leaveInitialFlight lif " +
      "LEFT JOIN t.returnArrivalFlight raf " +
      "LEFT JOIN t.returnInitialFlight rif " +
      "WHERE lif.departureAirport.id IN :departureAirportIds " +
      "AND ((laf IS NOT NULL AND laf.arrivalAirport.id IN :arrivalAirportIds) OR " +
      "(lif.arrivalAirport.id IN :arrivalAirportIds)) " +
      "AND ((raf IS NOT NULL AND raf.arrivalAirport.id IN :departureAirportIds) OR " +
      "(rif IS NOT NULL AND rif.arrivalAirport.id IN :departureAirportIds)) " +
      "AND lif.departureDate BETWEEN :departureDateLower AND :departureDateUpper " +
      "AND rif.departureDate BETWEEN :returnDateLower AND :returnDateUpper " +
      "ORDER BY lif.departureDate ASC")
  List<Trip> findRoundTripTripsByAirportIdsAndDateRange(@Param("departureAirportIds") List<Integer> departureAirportIds,
                                                        @Param("departureDateLower") LocalDateTime departureDateLower,
                                                        @Param("departureDateUpper") LocalDateTime departureDateUpper,
                                                        @Param("arrivalAirportIds") List<Integer> arrivalAirportIds,
                                                        @Param("returnDateLower") LocalDateTime returnDateLower,
                                                        @Param("returnDateUpper") LocalDateTime returnDateUpper,
                                                        Pageable pageable);

}
