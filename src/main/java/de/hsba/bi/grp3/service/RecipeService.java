package de.hsba.bi.grp3.service;


import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe createRecipe(String title){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);

        return repository.save(recipe);
    }

    public void addIngredient(Recipe recipe, Ingredient ingredient) {
        ingredient.setRecipe(recipe);
        recipe.getIngredientEntries().add(ingredient);
        saveRecipe(recipe);
    }

    public void deleteIngredient(Recipe recipe, Ingredient ingredient) {
        ingredient.setRecipe(recipe);
        recipe.getIngredientEntries().remove(ingredient);
        saveRecipe(recipe);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return repository.save(recipe);
    }

    public Recipe assignFormData(Recipe inputRecipeDate, Recipe recipe) {
        recipe.setTitle(inputRecipeDate.getTitle());
        recipe.setDescription(inputRecipeDate.getDescription());
        recipe.setInstruction(inputRecipeDate.getInstruction());
        recipe.setServings(inputRecipeDate.getServings());
        recipe.setPrepTime(inputRecipeDate.getPrepTime());
        recipe.setCookTime(inputRecipeDate.getCookTime());
        recipe.setDifficulty(inputRecipeDate.getDifficulty());
        recipe.setPrivat(inputRecipeDate.isPrivat());
        return recipe;
    }

    public void saveFormData(Long id, Recipe inputRecipeDate){
        Recipe recipe = getRecipe(id);
        recipe = assignFormData(inputRecipeDate, recipe);
        saveRecipe(recipe);
    }


    public Recipe getRecipe(Long id) { return repository.findById(id).orElse(null); }


    public Collection<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }
}
