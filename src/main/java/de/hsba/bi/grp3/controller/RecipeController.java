package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.exceptionHandlers.ForbiddenException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @ModelAttribute
    public void isUserAuthenticated (Model model){
        model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());
    }

    @GetMapping("/{id}")
    public String showSelectedRecipe(@PathVariable("id") Long id, Model model, @RequestParam(value = "amountServingsEntry", required = false, defaultValue = "") Integer amountServingsEntry, @ModelAttribute("AmountServingsForm") AmountServingsForm amountServingsForm) {
        Recipe recipe = recipeService.getRecipe(id);

        if (recipe.getIsPrivat() && !userService.isUserOwner(recipe.getOwner())){
            throw new ForbiddenException();
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
        //model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
        if (amountServingsEntry == null || amountServingsEntry <= 0) {
            model.addAttribute("amountServingsForm", new AmountServingsForm(recipe.getServings()));
            amountServingsEntry = recipe.getServings();
        }
        else {
            model.addAttribute("amountServingsForm", new AmountServingsForm(amountServingsEntry));
        }
        model.addAttribute("servingsFactor", recipeService.getServingsFactor(amountServingsEntry, recipe.getServings()));
        return "recipe/showRecipe";
    }



    @PostMapping(path = "/{id}/add-comment", params="action=addComment")
    public String createComment(@PathVariable("id") Long id, @ModelAttribute("commentForm") @Valid CommentForm commentForm, BindingResult commentBinding, Model model){
        if (commentBinding.hasErrors()) {
            Recipe recipe = recipeService.getRecipe(id);
            model.addAttribute("recipe", recipe);
            model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
            model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
            return "recipe/showRecipe";
        }
        User currentUser = userService.findCurrentUser();
        commentService.saveComment(commentFormConverter.createComment(new Comment(currentUser, recipeService.getRecipe(id)), commentForm));
        return "redirect:/recipe/" + id;
    }


    //EditRecipe Mappings
    @GetMapping("/edit/{id}")
    public String showEditableRecipe(@PathVariable("id") Long id, Model model) {
        if (!userService.isUserOwner(recipeService.getRecipe(id).getOwner())){
            throw new ForbiddenException();
        }
        // If there are any validation errors, they will be caught here in order to show the error-messages
        try {
            BindingResult bindingResultRecipe = (BindingResult) model.asMap().get("org.springframework.validation.BindingResult.recipeForm");
            if (bindingResultRecipe.hasErrors()) {
                model.addAttribute("ingredientForm", new IngredientForm());
            }
        } catch (NullPointerException e){
            model.addAttribute("recipeForm", recipeFormConverter.recipeToForm(recipeService.getRecipe(id)));
        }
        try {
            BindingResult bindingResultIngredient = (BindingResult) model.asMap().get("org.springframework.validation.BindingResult.ingredientForm");
            if (bindingResultIngredient.hasErrors()) {
                model.addAttribute("recipeForm", recipeFormConverter.recipeToForm(recipeService.getRecipe(id)));
            }
        } catch (NullPointerException e){
            model.addAttribute("ingredientForm", new IngredientForm());
        }

        // Load recipe properties
        model.addAttribute("recipe", recipeService.getRecipe(id));
        List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(UnitOfMeasure.values());
        model.addAttribute("unitOfMeasureList", unitOfMeasureList);
        List<Difficulty> difficultyList = Arrays.asList(Difficulty.values());
        model.addAttribute("difficultyList", difficultyList);
        return "recipe/editRecipe";
    }

    @GetMapping(path = "/edit/{id}/deleteIngredient/{indexId}")
    public String deleteIngredient(@PathVariable("id") Long id, @PathVariable(value="indexId") int ingredientListPos){
        if (!userService.isUserOwner(recipeService.getRecipe(id).getOwner())){
            throw new ForbiddenException();
        }
        Recipe recipe = recipeService.getRecipe(id);
        recipeService.deleteIngredientByIndexId(recipe, ingredientListPos);
        return "redirect:/recipe/edit/" + id;
    }

    @PostMapping(path = "/edit/{id}/add-ingredient", params="action=addIngredient")
    public String addIngredient(@PathVariable("id") Long id, @ModelAttribute("ingredientForm") @Valid final IngredientForm ingredientForm, final BindingResult ingredientBinding, Model model, RedirectAttributes redirectAttributes) {
        if (!userService.isUserOwner(recipeService.getRecipe(id).getOwner())){
            throw new ForbiddenException();
        }
        Recipe recipe = recipeService.getRecipe(id);
        if (ingredientBinding.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientForm", ingredientBinding);
            redirectAttributes.addFlashAttribute("ingredientForm", ingredientForm);
            redirectAttributes.addFlashAttribute("model", model);
            return "redirect:/recipe/edit/" + id;
        }
        recipeService.addIngredient(recipe, recipeFormConverter.updateIngredient(new Ingredient(), ingredientForm));
        return "redirect:/recipe/edit/" + id;
    }
    @PostMapping("/edit/{id}")
    public String saveAndShowRecipe(@PathVariable("id") Long id, @ModelAttribute("recipeForm") @Valid RecipeForm recipeForm, BindingResult recipeBinding, Model model, RedirectAttributes redirectAttributes){
        if (!userService.isUserOwner(recipeService.getRecipe(id).getOwner())){
            throw new ForbiddenException();
        }
        if (recipeBinding.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeForm", recipeBinding);
            redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
            redirectAttributes.addFlashAttribute("model", model);
            return "redirect:/recipe/edit/" + id;
        }
        recipeService.saveRecipe(recipeFormConverter.updateRecipe(recipeService.getRecipe(id),recipeForm));
        return "redirect:/recipe/" + id;
    }
    @PostMapping(path = "/edit/{id}/deleteRecipe")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.isUserOwner(recipeService.getRecipe(id).getOwner())){
            throw new ForbiddenException();
        }
        recipeService.deleteRecipe(id);
        return "redirect:/easy-recipe/";
    }

}
