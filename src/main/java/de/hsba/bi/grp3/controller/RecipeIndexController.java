package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.form.RecipeCreateForm;
import de.hsba.bi.grp3.form.RecipeForm;
import de.hsba.bi.grp3.form.RecipeFormConverter;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeIndexController {

    private final RecipeService recipeService;
    private final RecipeFormConverter formConverter;


    @GetMapping
    public String showFilteredRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        model.addAttribute("recipeForm", new RecipeForm());
        return "recipes/easyRecipeIndex";
    }

    @PostMapping
    public String createRecipe(String name, @ModelAttribute("recipeForm") @Valid RecipeCreateForm recipeCreateForm, BindingResult recipeBinding) {
        if (recipeBinding.hasErrors()) {
            return "recipes/easyRecipeIndex";
        }
        Recipe recipe = recipeService.saveRecipe(formConverter.createRecipe(new Recipe(), recipeCreateForm));
        // Recipe recipe = recipeService.createRecipe(name);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
}
