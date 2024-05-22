package no.ntnu.idata2306.y2024.g2.backend.db.repository;

import no.ntnu.idata2306.y2024.g2.backend.db.dto.PopularDestination;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for {@link Location} entities.
 * Extends {@link JpaRepository} to support custom queries and provide basic CRUD operations for the management of location entities.
 *
 * @author Daniel Neset
 * @version 18.05.2024
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
  @Query("SELECT new no.ntnu.idata2306.y2024.g2.backend.db.dto.PopularDestination(l, COUNT(f)) FROM Flight f " +
      "JOIN f.departureAirport da " +
      "JOIN f.arrivalAirport aa " +
      "JOIN aa.location l " +
      "WHERE f.arrivalDate BETWEEN :startDate AND :endDate " +
      "AND (:fromLocation IS NULL OR da.location = :fromLocation) " +
      "GROUP BY l ORDER BY COUNT(f) DESC")
  List<PopularDestination> findPopularDestinationsWithoutImage(@Param("fromLocation") Location fromLocation,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate,
                                                               Pageable pageable);

  @Query("SELECT new no.ntnu.idata2306.y2024.g2.backend.db.dto.PopularDestination(l, COUNT(f)) FROM Flight f " +
      "JOIN f.arrivalAirport aa " +
      "JOIN aa.location l " +
      "WHERE f.arrivalDate BETWEEN :startDate AND :endDate " +
      "AND (:fromLocation IS NULL OR f.departureAirport.location = :fromLocation) " +
      "AND l.image IS NOT NULL " +
      "GROUP BY l ORDER BY COUNT(f) DESC")
  List<PopularDestination> findPopularDestinationsWithImage(@Param("fromLocation") Location fromLocation,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate,
                                                            Pageable pageable);
}
