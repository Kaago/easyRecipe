package de.hsba.bi.grp3.controller;


import de.hsba.bi.grp3.exceptionHandlers.ForbiddenException;
import de.hsba.bi.grp3.form.RecipeFormConverter;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.exceptionHandlers.NotFoundException;
import de.hsba.bi.grp3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
public class RecipeShowController {

    private final RecipeService recipeService;
    private final RecipeFormConverter formConverter;
    private final UserService userService;
    @ModelAttribute("journal")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
             throw new NotFoundException();
        }
        return recipe;
    }

    @GetMapping
    public String showSelectedRecipe(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", getRecipeById(id));
        model.addAttribute("isUserOwner", userService.isUserOwner(getRecipeById(id).getOwner()));
        return "recipes/showRecipe";
    }



   /*@GetMapping
    public String editSelectedRecipe(@PathVariable("id") Long id) {
        Recipe recipe = getRecipeById(id);
        if(!recipe.isOwnedByCurrentUser()){
            throw new ForbiddenException();
        }
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }*/



/*    @PostMapping(path = "/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return "redirect:/recipes/";
    }*/
}
