package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	public Recipe findByTitle(String title);

	public boolean existsByTitle(String title);

	@Query(value="SELECT i FROM Ingredient i WHERE i.id NOT IN (SELECT ri.ingredient.id FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId)")
	Iterable<Ingredient> findIngredientsNotInRecipe(@Param("recipeId") Long recipeId);
	
}
