package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

	public Ingredient findByName(String name);

	public List<Ingredient> findByOrigin(String origin);

	public boolean existsByNameAndOrigin(String name, String origin);

	public Ingredient findByNameAndOrigin(String name, String origin);

}
