package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Difficulty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class RecipeForm {
    private Long id;

    @NotEmpty(message = "Please enter a Title!")
    @Size(max = 255, message = "The text is limited to 255 characters!")
    private String title;

    @Size(max = 255, message = "The text is limited to 255 characters!")
    private String description;

    @NotEmpty(message = "Please enter an Instruction!")
    private String instruction;

    @NotNull(message = "Please enter an amount!")
    @Min(value = 0, message = "no negative values")
    private Integer prepTime;

    @NotNull(message = "Please enter an amount!")
    @Min(value = 0, message = "no negative values")
    private Integer cookTime;

    @NotNull(message = "Please enter an amount!")
    @Min(value = 0, message = "no negative values")
    private Integer servings;

    @Enumerated
    private Difficulty difficulty;

    private Boolean isPrivat;
}
