package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.form.ChangePasswordForm;
import de.hsba.bi.grp3.form.CommentForm;
import de.hsba.bi.grp3.form.CommentFormConverter;
import de.hsba.bi.grp3.service.CommentService;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

    private final CommentService commentService;

    private final CommentFormConverter commentFormConverter;

    private final PasswordEncoder passwordEncoder;


    @GetMapping
    public String index(Model model) {

        User currentUser = userService.findCurrentUser();

        model.addAttribute("user",currentUser);
        model.addAttribute("userRecipes", recipeService.findRecipeByUser(currentUser));
        model.addAttribute("userComments", commentService.findUserComments(currentUser));

        return "user/userProfile";
    }
    @GetMapping("/editUser")
    public String editProfile(Model model) {

        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "user/editUserProfile";
    }

    @PostMapping("/editUser")
    public String changePassword(@Valid ChangePasswordForm changePasswordForm, Model model){

      String encodedPassword =  passwordEncoder.encode(changePasswordForm.getNewPassword());

       userService.changePassword(encodedPassword);

        User currentUser = userService.findCurrentUser();

        model.addAttribute("user",currentUser);
        model.addAttribute("userRecipes", recipeService.findRecipeByUser(currentUser));
        model.addAttribute("userComments", commentService.findUserComments(currentUser));

        return "user/userProfile";
    }


    @GetMapping("/editComment/{id}")
    public String editComment(@PathVariable("id") Long id, Model model){
        model.addAttribute("commentForm", commentFormConverter.toForm(commentService.getComment(id)));
        model.addAttribute("comment", commentService.getComment(id));
        return "user/editUserComment";
    }
    @PostMapping("/editComment/{id}")
    public String updateComment(@PathVariable("id") Long id, @Valid CommentForm commentForm, Model model){
        commentService.updateComment(commentFormConverter.update(commentService.getComment(id),commentForm));

        User currentUser = userService.findCurrentUser();

        model.addAttribute("user",currentUser);
        model.addAttribute("userRecipes", recipeService.findRecipeByUser(currentUser));
        model.addAttribute("userComments", commentService.findUserComments(currentUser));

        return "user/userProfile";
    }

}




