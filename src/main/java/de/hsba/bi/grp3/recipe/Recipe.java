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
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue()
    @Getter
    private Long id;

    private String title;

    private String description;

    private String instruction;

    private int prepTime;

    private int cookTime;

    private int servings;

    @Enumerated
    private Difficulty difficulty;

    private boolean isPrivat;


    @OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
    @OrderBy
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
