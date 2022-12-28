package be.buschop.ap2022b.party.repositories;

import be.buschop.ap2022b.party.model.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {


}
