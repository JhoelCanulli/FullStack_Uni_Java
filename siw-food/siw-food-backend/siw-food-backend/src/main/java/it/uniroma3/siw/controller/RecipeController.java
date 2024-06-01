package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.RecipeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeValidator recipeValidator;

	@Autowired
	private ChefService chefService;

	/*
	 * metodo che mappa la view della ricetta di cui è dato l'id user standard
	 */
	@GetMapping("{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(id));
		return "recipe.html";
	}

	/*
	 * metodo che mappa la view della lista delle ricette presenti nel sistema user
	 * standard
	 */
	@GetMapping("/recipes")
	public String showRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "recipes.html";
	}

	/*
	 * metodo che mappa la view con i parametri di ricerca user standard
	 */
	@GetMapping("/searchRecipes")
	public String searchRecipes(Model model) {
		return "searchRecipes.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per titolo user standard
	 */
	@GetMapping("/formSearchRecipesByTitle")
	public String formSearchRecipesByTitle(Model model) {
		return "formSearchRecipesByTitle.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per titolo user standard
	 */
	@PostMapping("/searchRecipesByTitle")
	public String searchRecipesByTitle(Model model, @RequestParam("title") String title) {
		Recipe found = this.recipeService.findByTitle(title);
		if (found != null) {
			return this.getRecipe(found.getId(), model);
		}
		return "nothingFound.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per autore user standard
	 */
	@GetMapping("/formSearchRecipesByAuthor")
	public String formSearchRecipesByAuthor(Model model) {
		return "formSearchRecipesByAuthor.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per autore user standard
	 */
	@PostMapping("/searchRecipesByAuthor")
	public String searchRecipesByAuthor(Model model, @RequestParam("name") String name,
			@RequestParam("surname") String surname) {
		// cerco se il nome e cognome producono una lista di chef nel sistema
		List<Chef> foundChefs = this.chefService.findByNameAndSurname(name, surname);
		if (!foundChefs.isEmpty()) {
			model.addAttribute("chefs", foundChefs);
			return "foundChefs.html";
		}
		// mando nothingFound, se non esiste
		return "nothingFound.html";
	}

	/*
	 * metodo che mappa la view-form per l'inserimento di una nuova ricetta user
	 * logged & admin
	 */
	@GetMapping("/formNewRecipe")
	public String formNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "formNewRecipe.html";
	}

	/*
	 * metodo che mappa la view-risposta per l'inserimento di una nuova ricetta user
	 * logged & admin
	 */
	@PostMapping("/new")
	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, Model model) {
		this.recipeValidator.validate(recipe, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.recipeService.save(recipe);
			model.addAttribute("recipe", recipe);
			return "redirect:/recipe/" + recipe.getId();
		} else {
			return "formNewRecipe.html";
		}
	}

	/*
	 * metodo che mappa la view per la lista di ricette modificabili admin
	 */
	@GetMapping("/manageRecipes")
	public String manageRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "manageRecipes.html";
	}

	/*
	 * metodo che mappa la view-form per l'aggiornamento dei dati user logged -
	 * admin
	 */
	@GetMapping("/formUpdateRecipe/{id}")
	public String formUpdateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(id));
		return "formUpdateRecipe.html";
	}

	/*
	 * metodo che mappa la view per l'aggiunta di un autore alla ricetta user logged
	 * - admin
	 */
	@GetMapping("/addAuthor/{id}")
	public String addAuthor(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		model.addAttribute("recipe", this.recipeService.findById(id));
		return "/chefsToAdd.html";
	}

	/*
	 * metodo che mappa l'operazione di aggiunta di un autore alla ricetta user
	 * logged - admin
	 */
	@GetMapping("/setAuthorToRecipe/{chefId}/{recipeId}")
	public String setAuthorToRecipe(@PathVariable("chefId") Long chefId, @PathVariable("recipeId") Long recipeId,
			Model model) {
		this.recipeService.setAuthorToRecipe(recipeId, chefId);
		return "redirect:/formUpdateRecipe/" + recipeId;
	}

	/*
	 * metodo che mappa la view-lista per la gestione di ingredienti della ricetta
	 * user logged - admin
	 */
	@GetMapping("/updateRecipeIngredients/{recipeId}")
	public String updateRecipeIngredients(@PathVariable("recipeId") Long recipeId, Model model) {
		List<Ingredient> ingredientsToAdd = this.recipeService.ingredientsToAdd(recipeId);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "ingredientsToAdd.html";
	}

	/*
	 * metodo che mappa l'operazione di aggiunta di una ingrediente alla ricetta
	 * user logged - admin
	 */
	// sugg chat: @RequestParam("quantityId") Long quantityId,
	@PostMapping("/addIngredientToRecipe/{ingredientId}/{recipeId}")
	public String addIngredientToRecipe(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, @RequestParam("quantity") String quantity, Model model) {

		this.recipeService.addRecipeIngredient(recipeId, ingredientId, quantity);

		List<Ingredient> ingredientsToAdd = this.recipeService.ingredientsToAdd(recipeId);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "redirect:/updateRecipeIngredients/" + recipeId;
	}

	/*
	 * metodo che mappa l'operazione di rimozione di una ingrediente dalla ricetta
	 * user logged - admin
	 */
	@GetMapping("/removeIngredientFromRecipe/{ingredientId}/{recipeId}")
	public String removeIngredientFromRecipe(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, Model model) {
		this.recipeService.removeRecipeIngredient(recipeId, ingredientId);

		List<Ingredient> ingredientsToAdd = this.recipeService.ingredientsToAdd(recipeId);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeService.findById(recipeId));

		return "/ingredientsToAdd.html";
	}
}
