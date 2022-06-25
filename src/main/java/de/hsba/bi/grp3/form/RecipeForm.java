package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Difficulty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;

@Getter
@Setter
public class RecipeForm {
    private Long id;

    private String title;

    private String description;

    private String instruction;

    private int prepTime;

    private int cookTime;

    private int servings;

    @Enumerated
    private Difficulty difficulty;

    private boolean isPrivat;
}
