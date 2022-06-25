package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeIndexController {

    private final RecipeService recipeService;

    @GetMapping
    public String showFilteredRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes/easyRecipeIndex";
    }

    @PostMapping
    public String createRecipe(String name) {
        Recipe recipe = recipeService.createRecipe(name);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
}
