package be.buschop.ap2022b.party.repositories;


import be.buschop.ap2022b.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean outdoor);
    Iterable<Venue> findByIndoor(boolean indoor);
    Iterable<Venue> findByCapacity(int capacity);
    Iterable<Venue> findByCapacityLessThan(int capacity);
    Iterable<Venue> findByCapacityBetween(int startCapacity, int endCapacity);
    Iterable<Venue> findByCapacityGreaterThanEqual(int capacity);



}
