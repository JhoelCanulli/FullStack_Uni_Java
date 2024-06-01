package it.uniroma3.siw.controller;

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

import it.uniroma3.siw.controller.validator.IngredientValidator;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.IngredientService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private IngredientValidator ingredientValidator;

	/*
	 * metodo che mappa la view dell'ingredienti di cui è dato l'id user standard
	 */
	@GetMapping("/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findById(id));
		return "ingredient.html";
	}

	/*
	 * metodo che mappa la view della lista degli ingredienti presenti nel sistema
	 * user standard
	 */
	@GetMapping("/ingredients")
	public String showRecipes(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		return "ingredients.html";
	}

	/*
	 * metodo che mappa la view con i parametri di ricerca user standard
	 */
	@GetMapping("/searchIngredients")
	public String searchIngredients(Model model) {
		return "searchIngredients.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per nome user standard
	 */
	@GetMapping("/formSearchIngredientsByName")
	public String formSearchIngredientsByName(Model model) {
		return "formSearchIngredientsByName.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per nome user standard
	 */
	@PostMapping("/searchIngredientsByName")
	public String searchIngredientsByName(Model model, @RequestParam("name") String name) {
		Ingredient found = this.ingredientService.findByName(name);
		if (found != null) {
			return this.getRecipe(found.getId(), model);
		}
		return "nothingFound.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per paese di orgine user standard
	 */
	@GetMapping("/formSearchIngredientsByOrigin")
	public String formSearchIngredientsByOrigin(Model model) {
		return "formSearchIngredientsByOrigin.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per paese di orgine user
	 * standard
	 */
	@PostMapping("/searchIngredientsByOrigin")
	public String searchIngredientsByOrigin(Model model, @RequestParam("origin") String origin) {
		model.addAttribute("ingredients", this.ingredientService.findByOrigin(origin));
		return "foundIngredients.html";
	}

	/*
	 * metodo che mappa la view-form per l'inserimento di un nuovo ingrediente user
	 * logged & admin
	 */
	@GetMapping("/formNewIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "formNewIngredient.html";
	}

	/*
	 * metodo che mappa la view-risposta per l'inserimento di un nuovo ingrediente
	 * user logged & admin
	 */
	@PostMapping("/new")
	public String newIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult bindingResult,
			Model model) {
		this.ingredientValidator.validate(ingredient, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.ingredientService.save(ingredient);
			model.addAttribute("ingredient", ingredient);
			return "redirect:/ingredient/" + ingredient.getId();
		} else {
			return "formNewIngredient.html";
		}
	}

}
