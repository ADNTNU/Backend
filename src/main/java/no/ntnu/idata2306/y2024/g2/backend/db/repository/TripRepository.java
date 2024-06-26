package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.dto.TripSearchResult;
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
  @Query("SELECT new no.ntnu.idata2306.y2024.g2.backend.db.dto.TripSearchResult(t) " +
      "FROM Trip t LEFT JOIN t.leaveArrivalFlight laf JOIN t.leaveInitialFlight lif " +
      "WHERE lif.departureAirport.id IN :departureAirportIds " +
      "AND ((laf IS NOT NULL AND laf.arrivalAirport.id IN :arrivalAirportIds) OR (laf IS NULL AND lif.arrivalAirport.id IN :arrivalAirportIds)) " +
      "AND t.returnInitialFlight IS NULL " +
      "AND t.leaveInitialFlight.departureDate BETWEEN :departureDateLower AND :departureDateUpper " +
      "GROUP BY t " +
      "ORDER BY t.leaveInitialFlight.departureDate ASC ")
  List<TripSearchResult> findOneWayTripsByAirportIdsAndDepartureDate(@Param("departureAirportIds") List<Integer> departureAirportIds,
                                                                     @Param("departureDateLower") LocalDateTime departureDateLower,
                                                                     @Param("departureDateUpper") LocalDateTime departureDateUpper,
                                                                     @Param("arrivalAirportIds") List<Integer> arrivalAirportIds,
                                                                     Pageable pageable);

  @Query("SELECT new no.ntnu.idata2306.y2024.g2.backend.db.dto.TripSearchResult(t) FROM Trip t " +
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
      "GROUP BY t " +
      "ORDER BY lif.departureDate ASC")
  List<TripSearchResult> findRoundTripTripsByAirportIdsAndDateRange(@Param("departureAirportIds") List<Integer> departureAirportIds,
                                                                    @Param("departureDateLower") LocalDateTime departureDateLower,
                                                                    @Param("departureDateUpper") LocalDateTime departureDateUpper,
                                                                    @Param("arrivalAirportIds") List<Integer> arrivalAirportIds,
                                                                    @Param("returnDateLower") LocalDateTime returnDateLower,
                                                                    @Param("returnDateUpper") LocalDateTime returnDateUpper,
                                                                    Pageable pageable);

  @Query("SELECT t FROM Trip t WHERE " +
      "t.leaveInitialFlight.id = :flightId OR " +
      "t.leaveArrivalFlight.id = :flightId OR " +
      "t.returnInitialFlight.id = :flightId OR " +
      "t.returnArrivalFlight.id = :flightId OR " +
      "EXISTS (SELECT 1 FROM t.leaveFlightIntervals df WHERE df.id = :flightId) OR " +
      "EXISTS (SELECT 1 FROM t.returnFlightIntervals rf WHERE rf.id = :flightId)")
  List<Trip> findTripsIncludingFlight(@Param("flightId") Integer flightId);
}
