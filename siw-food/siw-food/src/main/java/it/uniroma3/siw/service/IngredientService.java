package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.RecipeIngredient;
import it.uniroma3.siw.repository.IngredientRepository;
import it.uniroma3.siw.repository.RecipeIngredientRepository;
import jakarta.validation.Valid;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepo;
	
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepo;

	public Ingredient findById(Long id) {
		return this.ingredientRepo.findById(id).get();
	}

	public Iterable<Ingredient> findAll() {
		return this.ingredientRepo.findAll();
	}

	public Ingredient findByName(String name) {
		return this.ingredientRepo.findByName(name);
	}

	public List<Ingredient> findByOrigin(String origin) {
		return this.ingredientRepo.findByOrigin(origin);
	}

	public Ingredient save(@Valid Ingredient ingredient) {
		if (!this.ingredientRepo.existsByNameAndOrigin(ingredient.getName(), ingredient.getOrigin())) {
			//salvo il nuovo ingrediente
			Ingredient ingredientSaved=this.ingredientRepo.save(ingredient);
			
			//creo un nuovo ingrediente corrispondente da usare nelle ricette
			RecipeIngredient recipeIngredient=new RecipeIngredient();
			recipeIngredient.setIngredient(ingredient); //inizializzo corrispondenza
			this.recipeIngredientRepo.save(recipeIngredient); //salvo l'ingrediente per la ricetta
			
			return ingredientSaved;
		} else {
			return this.ingredientRepo.findByNameAndOrigin(ingredient.getName(), ingredient.getOrigin());
		}
	}
}
