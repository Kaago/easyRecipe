package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataCreator {

    private final RecipeService recipeService;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {

        // add example Journal for testing
        Recipe recipe = recipeService.createRecipe("Pizza");
    }
}
