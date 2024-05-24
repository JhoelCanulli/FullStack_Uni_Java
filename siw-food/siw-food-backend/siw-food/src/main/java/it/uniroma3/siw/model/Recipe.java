package it.uniroma3.siw.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String title;

	private String description;

	private String image;

	@ManyToOne
	private Chef writer;

	@OneToMany(mappedBy = "recipe")
	private Set<RecipeIngredient> recipeIngredients;

	public Recipe() {
		this.recipeIngredients = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Chef getWriter() {
		return writer;
	}

	public void setWriter(Chef writer) {
		this.writer = writer;
	}

	public Set<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public Set<Ingredient> getIngredients() {
		Set<Ingredient> out = new HashSet<>();
		for (RecipeIngredient ri : this.getRecipeIngredients()) {
			out.add(ri.getIngredient());
		}
		return out;
	}

	public Object getRecipeIngredient(Long ingredientId) {
		for (RecipeIngredient ri : this.recipeIngredients) {
			if (ri.getIngredient().getId().equals(ingredientId)) {
				return ri;
			}
		}
		return null;
	}

	public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, recipeIngredients, title, writer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(description, other.description)
				&& Objects.equals(recipeIngredients, other.recipeIngredients) && Objects.equals(title, other.title)
				&& Objects.equals(writer, other.writer);
	}

}
