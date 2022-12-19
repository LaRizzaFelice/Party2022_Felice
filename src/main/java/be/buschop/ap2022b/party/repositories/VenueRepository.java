package be.buschop.ap2022b.party.repositories;


import be.buschop.ap2022b.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean outdoor);
    Iterable<Venue> findByIndoor(boolean indoor);
    Iterable<Venue> findByCapacity(int capacity);
    Iterable<Venue> findByCapacityLessThan(int capacity);
    Iterable<Venue> findByCapacityBetween(int startCapacity, int endCapacity);

    @Query("select v from Venue v where :min IS NULL OR :min <= v.capacity")
    List<Venue> findByCapacityGreaterThan(@Param("min") Integer min);

    @Query("select v from Venue v where :min IS NULL or :max IS NULL OR :min <= v.capacity and :max >= v.capacity")
    List<Venue> findByCapacityBetween(@Param("min") Integer min,@Param("max") Integer max);

    @Query("select v from Venue v where v.capacity >= ?1")
    List<Venue> findByCapacityIsGreaterThanQuery (int min);

    @Query("select v from Venue v where v.capacity >= ?1 and v.capacity <= ?2")
    List<Venue> findByCapacityBetweenQuery(int min, int max);

    @Query("select v from Venue v where v.distanceFromPublicTransportInKm <= ?1")
            List<Venue> distanceFromPublicTransportInKm (int maxDistance);

    @Query("SELECT v FROM Venue v WHERE " +
            "(:minCapacity IS NULL OR :minCapacity <= v.capacity) AND " +
            "(:maxCapacity IS NULL OR v.capacity <= :maxCapacity) AND " +
            "(:maxDistance IS NULL OR v.distanceFromPublicTransportInKm <= :maxDistance) AND " +
            "(:foodProvided IS NULL OR v.foodProvided = :foodProvided) AND " +
            "(:indoor IS NULL OR v.indoor=:indoor) AND " +
            "(:outdoor IS NULL OR v.outdoor=:outdoor) ")
    List<Venue> findByFilter(@Param("minCapacity") Integer minCapacity,
                             @Param("maxCapacity") Integer maxCapacity,
                             @Param("maxDistance") Integer maxDistance,
                             @Param("foodProvided") Boolean foodProvided,
                             @Param("indoor") Boolean indoor,
                             @Param("outdoor") Boolean outdoor);




}
