package be.buschop.ap2022b.party.repositories;

import be.buschop.ap2022b.party.model.Party;
import be.buschop.ap2022b.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartyRepository extends CrudRepository<Party, Integer> {
    Iterable<Party> findByVenue(Venue venue);
}

