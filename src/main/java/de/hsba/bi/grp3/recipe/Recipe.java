package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Basic(optional = false)
    private String title;

    private String description;

    @Lob
    private String instruction;

    private BigDecimal prepTime;

    private BigDecimal cookTime;

    private BigDecimal servings;

    @Enumerated
    private Difficulty difficulty;

    private Boolean isPrivat;

    @ManyToOne(optional = false)
    @Getter
    private User owner;


    @OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private List<Ingredient> ingredients;


    // Constructor

    public Recipe(){}
    public Recipe( final User user) {
        this.owner = user;
    }
    public Recipe(String title, User owner) {
        this.title = title;
        this.owner = owner;
    }

    public Recipe(String title) {
        this.title = title;
    }


    public List<Ingredient> getIngredientEntries() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        return ingredients;
    }
}
