package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Component
public class ChefValidator implements Validator {

	@Autowired
	private ChefRepository chefRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Chef chef = (Chef) o;
		if (chef.getName() != null && chef.getSurname() != null && chef.getBirth() != null
				&& chefRepository.existsByNameAndSurnameAndBirth(chef.getName(), chef.getSurname(), chef.getBirth())) {
			errors.reject("chef.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Chef.class.equals(aClass);
	}
}
