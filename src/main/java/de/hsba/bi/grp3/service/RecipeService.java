package de.hsba.bi.grp3.service;


import de.hsba.bi.grp3.exceptionHandlers.NotFoundException;
import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.RecipeRepository;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RecipeService {


    private final RecipeRepository repository;
    private final UserService userService;

    public RecipeService(RecipeRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Recipe createRecipe(String title, User owner) {
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

    public Recipe getRecipe(Long id) {
        try {
            Recipe recipe = repository.findById(id).orElse(null);
            if (recipe != null) {
                return recipe;
            } else {
                throw new NotFoundException();
            }
        } catch (NullPointerException e) {
            throw new NotFoundException();
        }
    }

    public Collection<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    public List<Recipe> findRecipeBySearchText(String search, String order) {
        if (order.equals("HighestRating")) {
            return search.isBlank() ? repository.findAllRecipeOrderByRatingDesc() : repository.findRecipeBySearchTextOrderByRatingDesc(search.trim());
        }
        else if (order.equals("LowestRating")) {
            return search.isBlank() ? repository.findAllRecipeOrderByRatingAsc() : repository.findRecipeBySearchTextOrderByRatingAsc(search.trim());
        }
        else {
            List<Recipe> test = search.isBlank() ? repository.findAllPublicRecipe() : repository.findAllPublicRecipeBySearchText(search.trim());
            return test;
        }
    }

    public List<Recipe> findRecipeByUser(User user) {
        return repository.findRecipeByUser(user);
    }

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }

    public Double getServingsFactor (Integer factor, Integer base){
        if (base == null || base <= 0) {
            return 1D;
        }
        if (factor == null || factor <= 0) {
            return base.doubleValue();
        }
        return factor.doubleValue()/base.doubleValue();
    }
}
