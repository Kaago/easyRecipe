package de.hsba.bi.grp3.controller;


import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("recipes", recipeService.getAll());
        return "recipes/index";
    }

    @GetMapping(path = "/{id}")
    public String showRecipe(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipe(id));
        return "recipes/showRecipe";
    }

    @GetMapping(path = "/editRecipe/{id}")
    public String showEditableRecipe(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipe(id));
        return "recipes/editRecipe";
    }

    @PostMapping
    public String createRecipe(String name) {
        Recipe recipe = recipeService.createRecipe(name);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }

    @PostMapping(path = "/{id}")
    public String editRecipe(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        return "redirect:/recipes/editRecipe/" + recipe.getId();
    }

    @PostMapping(path = "/editRecipe/{id}")
    public String saveRecipe(@PathVariable("id") Long id, String name) {
        Recipe recipe = recipeService.getRecipe(id);
        return "redirect:/recipes/" + recipe.getId();
    }

    @PostMapping(path = "/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return "redirect:/recipes/";
    }
}
