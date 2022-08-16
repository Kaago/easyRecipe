package de.hsba.bi.grp3.controller;


import com.sun.xml.bind.v2.TODO;
import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.form.*;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.service.CommentService;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.exceptionHandlers.NotFoundException;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@Controller
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
public class RecipeShowController {

    private final RecipeService recipeService;
    private final RecipeFormConverter formConverter;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentFormConverter commentFormConverter;


    //TODO refactor journal
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
        Recipe recipe = getRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
        return "recipes/showRecipe";
    }



    @PostMapping
    public String createComment(@PathVariable("id") Long id, @ModelAttribute("commentForm") @Valid CommentForm commentForm, BindingResult commentBinding, Model model){
        if (commentBinding.hasErrors()) {
            Recipe recipe = getRecipeById(id);
            model.addAttribute("recipe", recipe);
            model.addAttribute("isUserOwner", userService.isUserOwner(recipe.getOwner()));
            model.addAttribute("recipeComments", commentService.getAllRecipeComments(recipe));
            return "recipes/showRecipe";
        }
        User currentUser = userService.findCurrentUser();
        commentService.saveComment(commentFormConverter.createComment(new Comment(currentUser, getRecipeById(id)), commentForm));
        return "redirect:/recipes/" + id;
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
