package be.buschop.ap2022b.party.repositories;

import be.buschop.ap2022b.party.model.Artist;
import be.buschop.ap2022b.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    Iterable<Artist> findArtistByArtistNameContainingIgnoreCase(String artistName);

    @Query("select a from Artist a where lower(a.artistName) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.genre) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.portfolio) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.bio) LIKE CONCAT('%', lower(:name), '%')  ")
    List<Artist> findByArtistName(@Param("name") String artistName);

}
