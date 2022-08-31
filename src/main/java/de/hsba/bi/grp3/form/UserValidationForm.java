package de.hsba.bi.grp3.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserValidationForm {


    @NotEmpty(message = "Please Choose a Name!")
    @NotNull
    private String name;

    @NotEmpty(message = "Please Choose a Password!")
    @NotNull
    private String passwordFirst;

    @NotEmpty(message = "Please Repeat the Password!")
    @NotNull
    private String passwordSecond;

}
