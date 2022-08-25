package de.hsba.bi.grp3.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AmountServingsForm {

    @Min(value=0, message = "This cannot be less than one")
    public Integer amountServings;

    public AmountServingsForm (Integer amountServings) {
        this.amountServings = amountServings;
    }
}
