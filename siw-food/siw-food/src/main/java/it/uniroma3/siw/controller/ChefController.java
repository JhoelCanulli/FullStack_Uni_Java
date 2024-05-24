package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.ChefService;
import jakarta.validation.Valid;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;

	/*
	 * metodo che mappa la view dello chef di cui è dato l'id user standard
	 */
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return "chef.html";
	}

	/*
	 * metodo che mappa la view della lista degli chef presenti nel sistema user
	 * standard
	 */
	@GetMapping("/chef")
	public String showChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chefs.html";
	}

	/*
	 * metodo che mappa la view con i parametri di ricerca user standard
	 */
	@GetMapping("/searchChefs")
	public String searchChefs(Model model) {
		return "searchChefs.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per nome user standard
	 */
	@GetMapping("/formSearchChefsByName")
	public String formSearchChefsByName(Model model) {
		return "formSearchChefsByName.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per nome user standard
	 */
	@PostMapping("/searchChefsByName")
	public String searchChefsByName(Model model, @RequestParam("name") String name,
			@RequestParam("surname") String surname) {
		if (!this.chefService.findByNameAndSurname(name, surname).isEmpty()
				|| this.chefService.findByNameAndSurname(name, surname) != null) {
			model.addAttribute("chefs", this.chefService.findByNameAndSurname(name, surname));
			return "foundChefs.html";
		}
		return "nothingFound.html";
	}

	/*
	 * metodo che mappa la view-form della ricerca per data di nascita user standard
	 */
	@GetMapping("/formSearchChefsByBirth")
	public String formSearchChefsByBirth(Model model) {
		return "formSearchChefsByBirth.html";
	}

	/*
	 * metodo che mappa la view-risposta della ricerca per data di nascita user
	 * standard
	 */
	@PostMapping("/searchChefsByBirth")
	public String searchChefsByBirth(Model model, @RequestParam("birth") LocalDate birth) {
		if (!this.chefService.findByBirth(birth).isEmpty() || this.chefService.findByBirth(birth) != null) {
			model.addAttribute("chefs", this.chefService.findByBirth(birth));
			return "foundChefs.html";
		}
		return "nothingFound.html";
	}

	/*
	 * metodo che mappa la view-form per l'inserimento di un nuovo chef user logged
	 * & admin
	 */
	@GetMapping("/formNewChef")
	public String formNewChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "formNewChef.html";
	}

	/*
	 * metodo che mappa la view-risposta per l'inserimento di un nuovo chef user
	 * logged & admin
	 */
	@PostMapping("/chef")
	public String newChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.chefService.save(chef);
			model.addAttribute("chef", chef);
			return "redirect:/chef/" + chef.getId();
		} else {
			return "formNewChef.html";
		}
	}

	/*
	 * metodo che mappa la view per la lista di chef modificabili admin
	 */
	@GetMapping("/manageChefs")
	public String manageChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "manageChefs.html";
	}

	/*
	 * metodo che mappa la view-form per l'aggiornamento dei dati di uno chef admin
	 */
	@GetMapping("/formUpdateChef/{chefId}")
	public String formUpdateChef(@PathVariable("chefId") Long chefId, Model model) {
		model.addAttribute("chef", this.chefService.findById(chefId));
		return "formUpdateChef.html";
	}

	/*
	 * metodo che mappa la view con la lista di ricette relative allo chefId user
	 * logged - admin
	 */
	@GetMapping("/updateRecipes/{chefId}")
	public String updateRecipes(@PathVariable("chefId") Long chefId, Model model) {
		List<Recipe> recipesToAdd = this.chefService.recipesToAdd(chefId);
		model.addAttribute("recipesToAdd", recipesToAdd);
		model.addAttribute("chef", this.chefService.findById(chefId));
		return "/recipesToAdd.html";
	}

	/*
	 * metodo che mappa l'operazione di aggiunta della ricetta allo chef user logged
	 * - admin
	 */
	@GetMapping("/addRecipeToChef/{recipeId}/{chefId}")
	public String addRecipeToChef(@PathVariable("recipeId") Long recipeId, @PathVariable("chefId") Long chefId,
			Model model) {
		this.chefService.addRecipeToChef(chefId, recipeId);
		List<Recipe> recipesToAdd = this.chefService.recipesToAdd(chefId);
		model.addAttribute("chef", this.chefService.findById(chefId));
		model.addAttribute("recipesToAdd", recipesToAdd);
		return "/recipesToAdd.html";
	}

	/*
	 * metodo che mappa l'operazione di rimozione della ricetta dallo chef user
	 * logged - admin
	 */
	@GetMapping("/removeRecipeFromChef/{recipeId}/{chefId}")
	public String removeRecipeFromChef(@PathVariable("recipeId") Long recipeId, @PathVariable("chefId") Long chefId,
			Model model) {
		this.chefService.removeRecipeFromChef(chefId, recipeId);
		List<Recipe> recipesToAdd = this.chefService.recipesToAdd(chefId);
		model.addAttribute("chef", this.chefService.findById(chefId));
		model.addAttribute("recipesToAdd", recipesToAdd);
		return "/recipesToAdd.html";
	}
}
