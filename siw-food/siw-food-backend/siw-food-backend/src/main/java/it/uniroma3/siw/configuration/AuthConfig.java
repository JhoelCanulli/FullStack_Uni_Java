package it.uniroma3.siw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AuthConfig {
/*
    @Autowired
    private DataSource dataSource;*/
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        int saltLength = 16;
        int hashLength = 32;
        int parallelism = 1;
        int memory = 4096;
        int iterations = 3;
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/", "/index", "/users/login", "/users/register", 
    								"/chef/{id}", "/chef/chefs", "/chef/searchChefs",
    								"/chef/formSearchChefsByBirth", "/formSearchChefsByBirth",
    								"/chef/searchChefsByBirth", "/chef/formNewChef", 
    								"/chef/new", "/chef/updateRecipes/{chefId}",
    								"/chef/addRecipeToChef/{recipeId}/{chefId}",
    								"/chef/removeRecipeFromChef/{recipeI}/{chefId}",
    								"/recipe/{id}", "/recipe/recipes", "/recipe/searchRecipes",
    								"/recipe/formSearchRecipesByTitle", "/recipe/searchRecipesByTitle",
                					"/recipe/formSearchRecipesByAuthor", "/recipe/searchRecipesByAuthor",
                					"/recipe/formNewRecipe", "/recipe/new", "/recipe/formUpdateRecipe/{id}",
                					"/recipe/addAuthor/{id}", "/recipe/setAuthorToRecipe/{chefId}/{recipeId}",
                					"/recipe/updateRecipeIngredients/{recipeId}", "/recipe/addIngredientToRecipe/{ingredientId}/{recipeId}",
                					"/recipe/removeIngredientFromRecipe/{ingredientId}/{recipeId}",
                					"/ingredient/{id}", "/ingredient/ingredients",  
                					"/ingredient/searchIngredients", "/ingredient/formSearchIngredientsByName",
                					"/ingredient/searchIngredientsByName", "/ingredient/formSearchIngredientsByOrigin",
                					"/ingredient/searchIngredientsByOrigin", "/ingredient/formNewIngredient", "/ingredient/new").hasRole("REGISTERED_COOK")
                .requestMatchers("/", "/index", "/users/login", "/users/register", 
			    					"/chef/{id}", "/chef/chefs", "/chef/searchChefs", 
			    					"/formSearchChefsByName", 
			    					"/searchChefsByName", "/chef/formSearchChefsByBirth",  
			    					"/chef/searchChefsByBirth",
			    					"/recipe/{id}", "/recipe/recipes", "/recipe/searchRecipes",
			    					"/recipe/formSearchRecipesByTitle", "/recipe/searchRecipesByTitle",
			    					"/recipe/formSearchRecipesByAuthor", "/recipe/searchRecipesByAuthor",
			    					"/ingredient/{id}", "/ingredient/ingredients",  
			    					"/ingredient/searchIngredients", "/ingredient/formSearchIngredientsByName",
			    					"/ingredient/searchIngredientsByName", "/ingredient/formSearchIngredientsByOrigin",
			    					"/ingredient/searchIngredientsByOrigin").permitAll()
                .requestMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/users/login")
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/index");
                })
            .and()
            .logout().permitAll();
        return http.build();
    }
}

