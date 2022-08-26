package de.hsba.bi.grp3.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue()
    @Getter
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private Integer amount;

    @Enumerated
    @Basic(optional = false)
    private UnitOfMeasure uom;

    @ManyToOne(optional = false)
    private Recipe recipe;


    // Constructor
    public Ingredient() {

    }

    public Ingredient(String name, Integer amount, UnitOfMeasure uom) {
        this.name = name;
        this.amount = amount;
        this.uom = uom;
    }
}
