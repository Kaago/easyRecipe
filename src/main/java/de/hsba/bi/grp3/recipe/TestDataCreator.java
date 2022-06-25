package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

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
        recipe.setPrepTime(20);
        recipe.setCookTime(60);
        recipe.setDifficulty(Difficulty.moderate);
        recipeService.addIngredient(recipe, new Ingredient("Tomaten", 400.0, UnitOfMeasure.g));
        recipeService.addIngredient(recipe, new Ingredient("Mehl", 500.0, UnitOfMeasure.g));
        recipeService.saveRecipe(recipe);
    }
}
