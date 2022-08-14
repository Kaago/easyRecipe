package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.recipe = :recipe")
    List<Comment> findRecipeComments(@Param("recipe") Recipe recipe);
}
