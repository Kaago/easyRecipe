package de.hsba.bi.grp3.recipe;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue()
    @Getter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String instruction;

    @Getter
    @Setter
    private int prepTime;

    @Getter
    @Setter
    private int cookTime;

    @Getter
    @Setter
    private int servings;

    @Getter
    @Setter
    @Enumerated
    private Difficulty difficulty;

    @Getter
    @Setter
    private boolean isPrivat;


    @Getter
    @Setter
    @OneToMany
    private List<Ingredient> ingredients;


    // Constructor
    public Recipe() {
    }


    public List<Ingredient> getIngredientEntries() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        return ingredients;
    }
}
