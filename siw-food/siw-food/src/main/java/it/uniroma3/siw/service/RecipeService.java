package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.RecipeIngredient;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.IngredientRepository;
import it.uniroma3.siw.repository.RecipeIngredientRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.validation.Valid;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private ChefRepository chefRepo;

	@Autowired
	private IngredientRepository ingredientRepo;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepo;

	public Recipe findById(Long id) {
		return this.recipeRepo.findById(id).get();
	}

	public Iterable<Recipe> findAll() {
		return this.recipeRepo.findAll();
	}

	public Recipe findByTitle(String title) {
		return this.recipeRepo.findByTitle(title);
	}

	public Recipe save(@Valid Recipe recipe) {
		if (!this.recipeRepo.existsByTitle(recipe.getTitle())) {
			return this.recipeRepo.save(recipe);
		} else {
			return this.recipeRepo.findByTitle(recipe.getTitle());
		}
	}

	public void setAuthorToRecipe(Long recipeId, Long chefId) {
		Recipe recipe = this.recipeRepo.findById(recipeId).get();
		Chef chef = this.chefRepo.findById(chefId).get();

		recipe.setWriter(chef);
		chef.getWritedRecipes().add(recipe);

		this.recipeRepo.save(recipe);
		this.chefRepo.save(chef);
	}

	public List<Ingredient> ingredientsToAdd(Long recipeId) {
		List<Ingredient> ingredientsToAdd = new ArrayList<>();
		for (Ingredient r : this.recipeRepo.findIngredientsNotInRecipe(recipeId)) {
			ingredientsToAdd.add(r);
		}
		return ingredientsToAdd;
	}

	public void addRecipeIngredient(Long recipeId, Long ingredientId, String quantity) {
		RecipeIngredient recipeIng = new RecipeIngredient();
		recipeIng.setIngredient(this.ingredientRepo.findById(ingredientId).get());
		recipeIng.setRecipe(this.recipeRepo.findById(recipeId).get());
		recipeIng.setQuantity(quantity);
		this.recipeIngredientRepo.save(recipeIng);
		this.recipeRepo.findById(recipeId).get().getRecipeIngredients().add(recipeIng);
	}

	public void removeRecipeIngredient(Long recipeId, Long ingredientId) {
		RecipeIngredient recipeIng = this.recipeIngredientRepo.findByRecipeAndIngredient(recipeId, ingredientId);
		this.recipeIngredientRepo.delete(recipeIng);
		this.recipeRepo.findById(recipeId).get().getRecipeIngredients().remove(recipeIng);
	}
}
