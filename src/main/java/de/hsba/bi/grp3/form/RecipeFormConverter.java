package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeFormConverter {


    public RecipeForm toForm(Recipe recipe) {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setTitle(recipe.getTitle());
        recipeForm.setDescription(recipe.getDescription());
        recipeForm.setInstruction(recipe.getInstruction());
        recipeForm.setServings(recipe.getServings());
        recipeForm.setDifficulty(recipe.getDifficulty());
        recipeForm.setPrepTime(recipe.getPrepTime());
        recipeForm.setCookTime(recipe.getCookTime());
        // recipeForm.setPrivat(recipe.isPrivat());
        return recipeForm;
    }

    public Recipe update(Recipe recipe, RecipeForm recipeForm) {
        recipe.setTitle(recipeForm.getTitle());
        recipe.setDescription(recipeForm.getDescription());
        recipe.setInstruction(recipeForm.getInstruction());
        recipe.setServings(recipeForm.getServings());
        recipe.setDifficulty(recipeForm.getDifficulty());
        recipe.setPrepTime(recipeForm.getPrepTime());
        recipe.setCookTime(recipeForm.getCookTime());
        return recipe;
    }

    public Recipe createRecipe(Recipe recipe, RecipeCreateForm recipeCreateForm){
        recipe.setTitle(recipeCreateForm.getTitle());
        return recipe;
    }

    public IngredientForm toForm(IngredientForm ingredientForm) {
        IngredientForm form = new IngredientForm();
        form.setName(ingredientForm.getName());
        form.setAmount(ingredientForm.getAmount());
        form.setUom(ingredientForm.getUom());
        return form;
    }

    public Ingredient update(Ingredient ingredient, IngredientForm ingredientForm) {
        ingredient.setName(ingredientForm.getName());
        ingredient.setAmount(ingredientForm.getAmount());
        ingredient.setUom(ingredientForm.getUom());
        return ingredient;
    }
}
