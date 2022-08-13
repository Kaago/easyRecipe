package de.hsba.bi.grp3.controller;


import de.hsba.bi.grp3.exceptionHandlers.NotFoundException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/editRecipe/{id}")
@RequiredArgsConstructor
public class RecipeEditController {

    private final RecipeService recipeService;
    private final RecipeFormConverter formConverter;

    @ModelAttribute("recipe")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
             throw new NotFoundException();
        }
        return recipe;
    }


    public void saveRecipe(@PathVariable("id") Long id, @ModelAttribute("recipeForm") RecipeForm recipeForm) {
        Recipe recipe = getRecipeById(id);
        recipeService.saveRecipe(formConverter.update(recipe, recipeForm));
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
        return "recipes/editRecipe";
    }

    @RequestMapping(path = "/addIngredient", params="action=addIngredient")
    public String addIngredient(@PathVariable("id") Long id, @ModelAttribute("ingredientForm") @Valid IngredientForm ingredientForm, BindingResult ingredientBinding, Model model) {
        Recipe recipe = getRecipeById(id);
        if (ingredientBinding.hasErrors()) {
            model.addAttribute("recipeForm", formConverter.toForm(recipe));
            List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
            model.addAttribute("unitOfMeasureList", unitOfMeasureList);
            List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
            model.addAttribute("difficultyList", difficultyList);
            return "recipes/editRecipe";
        }
        recipeService.addIngredient(recipe, formConverter.update(new Ingredient(), ingredientForm));
        return "redirect:/recipes/editRecipe/" + id;
    }
    @GetMapping(path = "/deleteIngredient/{indexId}")
    public String deleteIngredient(@PathVariable("id") Long id, @PathVariable(value="indexId") int ingredientListPos){
        Recipe recipe = recipeService.getRecipe(id);
        recipeService.deleteIngredientByIndexId(recipe, ingredientListPos);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
    @PostMapping
    public String saveAndShowRecipe(@PathVariable("id") Long id, @ModelAttribute("recipeForm") @Valid RecipeForm recipeForm, BindingResult recipeBinding, Model model){
        if (recipeBinding.hasErrors()) {
            model.addAttribute("ingredientForm", new IngredientForm());
            List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
            model.addAttribute("unitOfMeasureList", unitOfMeasureList);
            List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
            model.addAttribute("difficultyList", difficultyList);
            return "recipes/editRecipe";
        }
        saveRecipe(id, recipeForm);
        return "redirect:/recipes/" + id;
    }

    @PostMapping(path = "/deleteRecipe")
    public String delete(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipes/";
    }
}
