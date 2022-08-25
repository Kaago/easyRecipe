package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.recipe.UnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class IngredientForm {

    @NotEmpty(message = "Please enter a Name!")
    @Size(max = 255, message = "The text is limited to 255 characters!")
    private String name;

    @NotNull(message = "Please enter an amount!")
    private Integer amount;

    @Enumerated
    private UnitOfMeasure uom;

    private Recipe recipe;
}
