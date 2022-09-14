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

@Controller
@RequestMapping("/easy-recipe")
@RequiredArgsConstructor
public class HomepageController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final RecipeFormConverter recipeFormConverter;

    @ModelAttribute
    public void isUserAuthenticated (Model model){
        model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());
    }

    @GetMapping
    public String showFilteredRecipes(Model model, @RequestParam(value = "search", required = false, defaultValue = "") String search, @RequestParam(value = "sort", required = false, defaultValue = "HighestRating") String sort) {
        model.addAttribute("recipes", recipeService.findRecipeBySearchText(search, sort)); // search method for Searching after recipe titles and ingredients
        model.addAttribute("search", search); // Attribute for Searching after recipe titles and ingredients
        model.addAttribute("sort", sort); // Attribute for ordering the search results an either highest rated recipes or lowest rated recipes
        model.addAttribute("recipeForm", new RecipeForm()); // Form for creating a new recipe (just the title)
        return "easyRecipeIndex";
    }

    @PostMapping
    public String createRecipe(Model model, @ModelAttribute("recipeForm") @Valid RecipeCreateForm recipeCreateForm, BindingResult recipeBinding, @RequestParam(value = "search", required = false, defaultValue = "") String search, @RequestParam(value = "sort", required = false, defaultValue = "HighestRating") String sort) {
        // Catch the validation errors of the RecipeCreateForm (when no title was put into the text-box)
        if (recipeBinding.hasErrors()) {
            model.addAttribute("recipes", recipeService.findRecipeBySearchText(search, sort));
            model.addAttribute("search", search);
            model.addAttribute("sort", sort);
            return "easyRecipeIndex";
        }

        User currentUser = userService.findCurrentUser();
        // Creating the recipe
        Recipe recipe = recipeService.saveRecipe(recipeFormConverter.createRecipe(new Recipe(currentUser), recipeCreateForm));
        return "redirect:/recipe/edit/" + recipe.getId();
    }

}
