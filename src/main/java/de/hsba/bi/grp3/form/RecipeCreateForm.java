package de.hsba.bi.grp3.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RecipeCreateForm {
    private Long id;

    @NotEmpty(message = "Please enter a Title!")
    private String title;
}
