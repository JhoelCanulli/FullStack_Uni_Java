package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.RecipeIngredient;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, Long> {

	@Query(value = "SELECT ri FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId AND ri.ingredient.id = :ingredientId")
	RecipeIngredient findByRecipeAndIngredient(@Param("recipeId") Long recipeId,
			@Param("ingredientId") Long ingredientId);

}
