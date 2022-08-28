package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.service.CommentService;
import de.hsba.bi.grp3.service.RecipeService;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataCreator {

    private final RecipeService recipeService;
    private  final UserService userService;
    private final CommentService commentService;
    private final  PasswordEncoder passwordEncoder;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {

        User test = new User("Test",passwordEncoder.encode("Test"), User.USER_ROLE);
        userService.save(test);
        User test1 = new User("Test1",passwordEncoder.encode("Test1"), User.USER_ROLE);
        userService.save(test1);


        // add example Journal for testing
        Recipe recipe = recipeService.createRecipe("Zimt-Koriander-Bällchen in Pfeffer-Paprikasauce", test);
        recipe.setDescription("orientalisch angehaucht, mit tollem Zimtaroma");
        recipe.setInstruction("Das Hackfleisch mit Semmelbröseln, Zimt, Koriander, Paprikapulver und Meersalz vermengen und kleine Bällchen daraus formen.");
        recipe.setServings(2);
        recipe.setPrepTime(20);
        recipe.setCookTime(60);
        recipe.setIsPrivat(false);
        recipe.setDifficulty(Difficulty.moderate);
        recipe.setOwner(test);
        recipeService.addIngredient(recipe, new Ingredient("Tomaten", 400, UnitOfMeasure.g));
        recipeService.addIngredient(recipe, new Ingredient("Mehl", 500, UnitOfMeasure.g));
        recipeService.saveRecipe(recipe);
        commentService.createComment("Test", 2F, test, recipe);

        // add example Journal for testing
        Recipe recipe1 = recipeService.createRecipe("Pizza", test);
        recipe1.setDescription("Italienische Art");
        recipe1.setInstruction("Das Hackfleisch mit Semmelbröseln, Zimt, Koriander, Paprikapulver und Meersalz vermengen und kleine Bällchen daraus formen.");
        recipe1.setServings(2);
        recipe1.setPrepTime(20);
        recipe1.setCookTime(60);
        recipe1.setIsPrivat(false);
        recipe1.setDifficulty(Difficulty.moderate);
        recipe1.setOwner(test);
        recipeService.addIngredient(recipe1, new Ingredient("Tomaten", 400, UnitOfMeasure.g));
        recipeService.addIngredient(recipe1, new Ingredient("Mehl", 500, UnitOfMeasure.g));
        recipeService.saveRecipe(recipe1);
        commentService.createComment("Test", 4F, test, recipe1);
    }

}
