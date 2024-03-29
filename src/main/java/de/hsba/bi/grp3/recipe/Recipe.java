package de.hsba.bi.grp3.recipe;

import de.hsba.bi.grp3.comment.Comment;
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
@Table(name = "recipe")
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

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    @Enumerated
    private Difficulty difficulty;

    private Boolean isPrivat;

    @ManyToOne(optional = false)
    @Getter
    private User owner;


    @OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private List<Ingredient> ingredients;


    @OneToMany(mappedBy = "recipe", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH }, orphanRemoval = true)
    private List<Comment> comments;

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
