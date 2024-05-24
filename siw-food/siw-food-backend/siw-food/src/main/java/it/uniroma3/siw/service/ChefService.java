package it.uniroma3.siw.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.validation.Valid;

@Service
public class ChefService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ChefRepository chefRepo;
	
	@Autowired
	private RecipeRepository recipeRepo;

	public Chef findById(Long id) {
		return this.chefRepo.findById(id).get();
	}

	public Iterable<Chef> findAll() {
		return this.chefRepo.findAll();
	}

	public List<Chef> findByNameAndSurname(String name, String surname) {
		return this.chefRepo.findByNameAndSurname(name, surname);
	}

	public List<Chef> findByBirth(LocalDate birth) {
		return this.chefRepo.findByBirth(birth);
	}

	public Chef save(@Valid Chef chef) {
		if (!this.chefRepo.existsByNameAndSurnameAndBirth(chef.getName(), chef.getSurname(), chef.getBirth())) {
			return this.chefRepo.save(chef);
		} else {
			return this.chefRepo.findByNameAndSurnameAndBirth(chef.getName(), chef.getSurname(), chef.getBirth());
		}
	}

	public List<Recipe> recipesToAdd(Long chefId) {
		List<Recipe> recipesToAdd = new ArrayList<>();
		for (Recipe r : this.chefRepo.findRecipesNotWritedByChef(chefId)) {
			recipesToAdd.add(r);
		}
		return recipesToAdd;
	}

	public void addRecipeToChef(Long chefId, Long recipeId) {
		Chef chef = this.chefRepo.findById(chefId).get();
		Recipe recipe = this.recipeRepo.findById(recipeId).get();
		chef.getWritedRecipes().add(recipe);
		recipe.setWriter(chef);
		this.chefRepo.save(chef);
	}

	public void removeRecipeFromChef(Long chefId, Long recipeId) {
		Chef chef = this.chefRepo.findById(chefId).get();
		Recipe recipe = this.recipeRepo.findById(recipeId).get();
		chef.getWritedRecipes().remove(recipe);
		recipe.setWriter(null);
		this.chefRepo.save(chef);
	}
	
	public Chef registerChef(Chef chef) {
        String encodedPassword = bCryptPasswordEncoder.encode(chef.getPassword());
        chef.setPassword(encodedPassword);
        return chefRepo.save(chef);
    }
}
