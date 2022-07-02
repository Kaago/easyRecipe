package de.hsba.bi.grp3.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RecipeCreateForm {
    private Long id;

    @NotEmpty(message = "Please enter a Title!")
    @Size(max = 255, message = "The text is limited to 255 characters!")
    private String title;
}
