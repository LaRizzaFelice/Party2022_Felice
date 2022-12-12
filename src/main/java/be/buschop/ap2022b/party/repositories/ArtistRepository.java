package be.buschop.ap2022b.party.repositories;

import be.buschop.ap2022b.party.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    Iterable<Artist> findArtistByArtistName(String artistName);

}
