package de.hsba.bi.grp3.service;

import de.hsba.bi.grp3.repository.RecipeRepository;
import de.hsba.bi.grp3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceGetServingsFactorTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RecipeService recipeService;

    @Test
    void getServingsFactorFactorBasePositiv() {

        //Arrange
        Integer factor = 1;
        Integer base = 2;
        // Action
        Double response = recipeService.getServingsFactor(factor, base);
        // Assert
        assertEquals(0.5D, response);
    }

    @Test
    void getServingsFactorBaseNull() {

        //Arrange
        Integer factor = 1;
        Integer base = null;
        // Action
        Double response = recipeService.getServingsFactor(factor, base);
        // Assert
        assertEquals(1D, response);
    }

    @Test
    void getServingsFactorBaseEqualOrLessThanZero() {

        //Arrange
        Integer factor = 1;
        Integer base = 0;
        // Action
        Double response = recipeService.getServingsFactor(factor, base);
        // Assert
        assertEquals(1D, response);
    }

    @Test
    void getServingsFactorFactorNull() {

        //Arrange
        Integer factor = 0;
        Integer base = 2;
        // Action
        Double response = recipeService.getServingsFactor(factor, base);
        // Assert
        assertEquals(base.doubleValue(), response);
    }

    @Test
    void getServingsFactorFactorNegativ() {

        //Arrange
        Integer factor = -1;
        Integer base = 2;
        // Action
        Double response = recipeService.getServingsFactor(factor, base);
        // Assert
        assertEquals(base.doubleValue(), response);
    }
}