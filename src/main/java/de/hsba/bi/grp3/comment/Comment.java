package de.hsba.bi.grp3.comment;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Comment {


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Recipe recipe;

    @Id
    @GeneratedValue()
    private Long id;

    @Lob
    private String textContent;

    private float rating;

    @ManyToOne(optional = false)
    private User owner;

    public Comment(){}
    public Comment(User owner, Recipe recipe){
        this.owner = owner;
        this.recipe = recipe;
    }
    public Comment(String textContent, float rating, User owner, Recipe recipe){
        this.textContent = textContent;
        this.rating = rating;
        this.owner = owner;
        this.recipe = recipe;
    }
}
