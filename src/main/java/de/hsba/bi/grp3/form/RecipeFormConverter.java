package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class RecipeFormConverter {


    public RecipeForm recipeToForm(Recipe recipe) {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setTitle(recipe.getTitle());
        recipeForm.setDescription(recipe.getDescription());
        recipeForm.setInstruction(recipe.getInstruction());
        recipeForm.setServings(recipe.getServings());
        recipeForm.setDifficulty(recipe.getDifficulty());
        recipeForm.setPrepTime(recipe.getPrepTime());
        recipeForm.setCookTime(recipe.getCookTime());
        recipeForm.setIsPrivat(recipe.getIsPrivat());
        return recipeForm;
    }

    public Recipe updateRecipe(Recipe recipe, RecipeForm recipeForm) {
        recipe.setTitle(recipeForm.getTitle());
        recipe.setDescription(recipeForm.getDescription());
        recipe.setInstruction(recipeForm.getInstruction());
        recipe.setServings(recipeForm.getServings());
        recipe.setDifficulty(recipeForm.getDifficulty());
        recipe.setPrepTime(recipeForm.getPrepTime());
        recipe.setCookTime(recipeForm.getCookTime());
        recipe.setIsPrivat(recipeForm.getIsPrivat());
        return recipe;
    }

    public Recipe createRecipe(Recipe recipe, RecipeCreateForm recipeCreateForm){
        recipe.setTitle(recipeCreateForm.getTitle());
        recipe.setIsPrivat(true);
        return recipe;
    }

    public IngredientForm ingredientToForm(IngredientForm ingredientForm) {
        IngredientForm form = new IngredientForm();
        form.setName(ingredientForm.getName());
        form.setAmount(ingredientForm.getAmount());
        form.setUom(ingredientForm.getUom());
        return form;
    }

    public Ingredient updateIngredient(Ingredient ingredient, IngredientForm ingredientForm) {
        ingredient.setName(ingredientForm.getName());
        ingredient.setAmount(ingredientForm.getAmount());
        ingredient.setUom(ingredientForm.getUom());
        return ingredient;
    }
}
