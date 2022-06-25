package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.recipe.UnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;

@Getter
@Setter
public class IngredientForm {

    private String name;
    private Double amount;

    @Enumerated
    private UnitOfMeasure uom;

    private Recipe recipe;
}
