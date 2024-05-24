package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {

	public List<Chef> findByNameAndSurname(String name, String surname);

	public List<Chef> findByBirth(LocalDate birth);

	public boolean existsByNameAndSurnameAndBirth(String name, String surname, LocalDate birth);

	public Chef findByNameAndSurnameAndBirth(String name, String surname, LocalDate birth);
	
	@Query(value="SELECT r FROM Recipe r WHERE r.writer.id <> :chefId OR r.writer IS NULL")
	Iterable<Recipe> findRecipesNotWritedByChef(@Param("chefId") Long chefId);
}
