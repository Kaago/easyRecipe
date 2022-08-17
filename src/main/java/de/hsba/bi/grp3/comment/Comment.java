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

    @Id
    @GeneratedValue()
    private Long id;

    @Lob
    private String textContent;

    private float rating;

    @ManyToOne(optional = false)
    private User owner;

    @ManyToOne(cascade=CascadeType.ALL,optional = false)
    private Recipe recipe;

    public Comment(){}
    public Comment(User owner, Recipe recipe){
        this.owner = owner;
        this.recipe = recipe;
    }
    public Comment(String textContent, Long rating, User owner, Recipe recipe){
        this.textContent = textContent;
        this.rating = rating;
        this.owner = owner;
        this.recipe = recipe;
    }
}
