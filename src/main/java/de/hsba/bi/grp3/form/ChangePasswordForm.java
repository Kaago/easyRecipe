package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangePasswordForm {
    private Long id;

    @NotEmpty(message = "Please enter a comment message!")
    private String newPassword;

}
