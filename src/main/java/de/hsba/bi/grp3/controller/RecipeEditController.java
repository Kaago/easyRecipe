package de.hsba.bi.grp3.controller;


import de.hsba.bi.grp3.form.IngredientForm;
import de.hsba.bi.grp3.form.RecipeForm;
import de.hsba.bi.grp3.form.RecipeFormConverter;
import de.hsba.bi.grp3.recipe.Difficulty;
import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.recipe.UnitOfMeasure;
import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/recipes/editRecipe/{id}")
@RequiredArgsConstructor
public class RecipeEditController {

    private final RecipeService recipeService;
    private final RecipeFormConverter formConverter;

    @ModelAttribute("journal")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            // throw new NotFoundException();
        }
        return recipe;
    }

    @GetMapping
    public String showEditableRecipe(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipeForm", formConverter.toForm(getRecipeById(id)));
        model.addAttribute("ingredientForm", new IngredientForm());
        model.addAttribute("recipe", recipeService.getRecipe(id));
        List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
        model.addAttribute("unitOfMeasureList", unitOfMeasureList);
        List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
        model.addAttribute("difficultyList", difficultyList);
        List<Ingredient> ingredientList = recipeService.getRecipe(id).getIngredients();
        model.addAttribute("ingredientList", ingredientList);
        return "recipes/editRecipe";
    }

    @RequestMapping(path = "/addIngredient", params="action=addIngredient")
    public String addIngredient(@PathVariable("id") Long id, @ModelAttribute Recipe recipeData, Ingredient ingredient) {
        Recipe recipe = recipeService.getRecipe(id);
        recipeService.addIngredient(recipe, ingredient);
        recipeService.saveFormData(id, recipeData);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
    @RequestMapping(path = "/deleteIngredient", params="action=deleteIngredient")
    public String deleteIngredient(@PathVariable("id") Long id, @ModelAttribute Recipe recipeData, @RequestParam(value="ingredientListPos") int ingredientListPos){
        Recipe recipe = recipeService.getRecipe(id);
        Ingredient ingredient = recipe.getIngredientEntries().get(ingredientListPos);
        recipeService.deleteIngredient(recipe, ingredient);
        recipeService.saveFormData(id, recipeData);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
    @PostMapping
    public String saveRecipe(@PathVariable("id") Long id, @ModelAttribute("recipeForm") RecipeForm recipeForm){
        Recipe recipe = getRecipeById(id);
        recipeService.saveRecipe(formConverter.update(recipe, recipeForm));
        return "redirect:/recipes/" + id;
    }

/*    @PostMapping
    public String delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return "redirect:/recipes/";
    }*/
}
