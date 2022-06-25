package de.hsba.bi.grp3.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue()
    @Getter
    private Long id;

    private String name;
    private Double amount;

    @Enumerated
    private UnitOfMeasure uom;

    @ManyToOne(optional = false)
    private Recipe recipe;


    // Constructor
    public Ingredient() {

    }

    public Ingredient(String name, Double amount, UnitOfMeasure uom) {
        this.name = name;
        this.amount = amount;
        this.uom = uom;
    }
}
