package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentForm {
    private Long id;

    @NotEmpty(message = "Please enter a comment message!")
    private String textContent;

    @NotNull()
    @Min(message = "Test", value = 0L)
    private float rating;

    @ManyToOne(optional = false)
    private User owner;

    @ManyToOne(optional = false)
    private Recipe recipe;
}
