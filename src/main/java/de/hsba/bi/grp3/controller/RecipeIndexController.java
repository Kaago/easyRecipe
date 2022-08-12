package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.form.RecipeCreateForm;
import de.hsba.bi.grp3.form.RecipeForm;
import de.hsba.bi.grp3.form.RecipeFormConverter;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeIndexController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final RecipeFormConverter formConverter;


    @GetMapping
    public String showFilteredRecipes(Model model, @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        model.addAttribute("recipes", recipeService.findRecipeBySearchText(search));
        model.addAttribute("search", search);
        model.addAttribute("recipeForm", new RecipeForm());
        return "recipes/easyRecipeIndex";
    }

    @PostMapping
    public String createRecipe(String name, @ModelAttribute("recipeForm") @Valid RecipeCreateForm recipeCreateForm, BindingResult recipeBinding) {
        if (recipeBinding.hasErrors()) {
            return "recipes/easyRecipeIndex";
        }

        User currentUser = userService.findCurrentUser();
        Recipe recipe = recipeService.saveRecipe(formConverter.createRecipe(new Recipe(currentUser), recipeCreateForm));
        // Recipe recipe = recipeService.createRecipe(name);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }
}
