package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.recipe.UnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class IngredientForm {

    @NotEmpty(message = "Please enter a Name!")
    private String name;

    @NotNull(message = "Please enter an amount!")
    private BigDecimal amount;

    @Enumerated
    private UnitOfMeasure uom;

    private Recipe recipe;
}
