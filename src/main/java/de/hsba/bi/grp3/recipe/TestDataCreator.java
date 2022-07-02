package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataCreator {

    private final RecipeService recipeService;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {

        // add example Journal for testing
        Recipe recipe = recipeService.createRecipe("Zimt-Koriander-Bällchen in Pfeffer-Paprikasauce");
        recipe.setDescription("orientalisch angehaucht, mit tollem Zimtaroma");
        recipe.setInstruction("Das Hackfleisch mit Semmelbröseln, Zimt, Koriander, Paprikapulver und Meersalz vermengen und kleine Bällchen daraus formen.");
        recipe.setServings(new BigDecimal(1));
        recipe.setPrepTime(new BigDecimal(20));
        recipe.setCookTime(new BigDecimal(60));
        recipe.setDifficulty(Difficulty.moderate);
        recipeService.addIngredient(recipe, new Ingredient("Tomaten", new BigDecimal(400), UnitOfMeasure.g));
        recipeService.addIngredient(recipe, new Ingredient("Mehl", new BigDecimal(500), UnitOfMeasure.g));
        recipeService.saveRecipe(recipe);
    }
}
