package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;

@Entity
public class Chef{

	private static final long serialVersionUID = 1L;
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
	private String name;

	private String surname;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;

	private String photo;

	@OneToMany(mappedBy = "writer")
	private List<Recipe> writedRecipes;

	public Chef() {
		this.writedRecipes = new LinkedList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Recipe> getWritedRecipes() {
		return writedRecipes;
	}

	public void setWritedRecipes(List<Recipe> writedRecipes) {
		this.writedRecipes = writedRecipes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birth, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chef other = (Chef) obj;
		return Objects.equals(birth, other.birth) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}

}
