package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.exceptionHandlers.NotFoundException;
import de.hsba.bi.grp3.form.*;
import de.hsba.bi.grp3.recipe.Difficulty;
import de.hsba.bi.grp3.recipe.Ingredient;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.recipe.UnitOfMeasure;
import de.hsba.bi.grp3.service.CommentService;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentFormConverter commentFormConverter;
    private final RecipeFormConverter recipeFormConverter;

    //custom function to catch internal 500 error
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            throw new NotFoundException();
        }
        return recipe;
    }

    @GetMapping("/{id}")
    public String showSelectedRecipe(@PathVariable("id") Long id, Model model) {
        Recipe recipe = getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
        return "recipe/showRecipe";
    }



    @PostMapping(path = "/{id}/add-comment", params="action=addComment")
    public String createComment(@PathVariable("id") Long id, @ModelAttribute("commentForm") @Valid CommentForm commentForm, BindingResult commentBinding, Model model){
        if (commentBinding.hasErrors()) {
            Recipe recipe = getRecipeById(id);
            model.addAttribute("recipe", recipe);
            model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
            model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
            return "recipe/showRecipe";
        }
        User currentUser = userService.findCurrentUser();
        commentService.saveComment(commentFormConverter.createComment(new Comment(currentUser, getRecipeById(id)), commentForm));
        return "redirect:/recipe/" + id;
    }


    //EditRecipe Mappings


    @GetMapping("/edit/{id}")
    public String showEditableRecipe(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipeForm", recipeFormConverter.toForm(getRecipeById(id)));
        model.addAttribute("ingredientForm", new IngredientForm());
        model.addAttribute("recipe", recipeService.getRecipe(id));
        List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
        model.addAttribute("unitOfMeasureList", unitOfMeasureList);
        List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
        model.addAttribute("difficultyList", difficultyList);
        return "recipe/editRecipe";
    }
    @GetMapping(path = "/deleteIngredient/{indexId}")
    public String deleteIngredient(@PathVariable("id") Long id, @PathVariable(value="indexId") int ingredientListPos){
        Recipe recipe = recipeService.getRecipe(id);
        recipeService.deleteIngredientByIndexId(recipe, ingredientListPos);
        return "redirect:/recipe/editRecipe/" + recipe.getId();
    }

    @PostMapping(path = "/edit/{id}/add-ingredient", params="action=addIngredient")
    public String addIngredient(@PathVariable("id") Long id, @ModelAttribute("ingredientForm") @Valid IngredientForm ingredientForm, BindingResult ingredientBinding, Model model) {
        Recipe recipe = getRecipeById(id);
        if (ingredientBinding.hasErrors()) {
            model.addAttribute("recipeForm", recipeFormConverter.toForm(recipe));
            List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
            model.addAttribute("unitOfMeasureList", unitOfMeasureList);
            List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
            model.addAttribute("difficultyList", difficultyList);
            return "recipe/editRecipe";
        }
        recipeService.addIngredient(recipe, recipeFormConverter.update(new Ingredient(), ingredientForm));
        return "redirect:/recipe/edit/" + id;
    }
    @PostMapping("/edit/{id}")
    public String saveAndShowRecipe(@PathVariable("id") Long id, @ModelAttribute("recipeForm") @Valid RecipeForm recipeForm, BindingResult recipeBinding, Model model){
        if (recipeBinding.hasErrors()) {
            model.addAttribute("ingredientForm", new IngredientForm());
            List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
            model.addAttribute("unitOfMeasureList", unitOfMeasureList);
            List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
            model.addAttribute("difficultyList", difficultyList);
            return "recipe/editRecipe";
        }

        recipeService.saveRecipe(recipeFormConverter.update(getRecipeById(id),recipeForm));
        return "redirect:/recipe/" + id;
    }
    @PostMapping(path = "/edit/{id}/deleteRecipe")
    public String delete(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipe/";
    }

}