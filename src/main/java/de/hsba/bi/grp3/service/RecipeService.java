package de.hsba.bi.grp3.service;


import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.RecipeRepository;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe createRecipe(String title, User owner){
        Recipe recipe = new Recipe(title, owner);
        return saveRecipe(recipe);
    }

    public void addIngredient(Recipe recipe, Ingredient ingredient) {
        ingredient.setRecipe(recipe);
        recipe.getIngredientEntries().add(ingredient);
        saveRecipe(recipe);
    }

    public void deleteIngredientByIndexId(Recipe recipe, int indexId) {
        List<Ingredient> ingredients = recipe.getIngredientEntries();
        Ingredient ingredient = ingredients.get(indexId);
        ingredients.remove(ingredient);
        saveRecipe(recipe);
    }



    public Recipe saveRecipe(Recipe recipe) {
        return repository.save(recipe);
    }


    public Recipe getRecipe(Long id) { return repository.findById(id).orElse(null); }


    public Collection<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    public List<Recipe> findRecipeBySearchText(String search) {
        return search.isBlank() ? repository.findAll() : repository.findRecipeBySearchText(search.trim());
    }
    public List<Recipe> findRecipeByUser(User user) {
        return repository.findRecipeByUser(user);
    }

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }


}
