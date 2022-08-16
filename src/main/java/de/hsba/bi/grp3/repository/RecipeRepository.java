package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select distinct r from Recipe r join r.ingredients i where i.name like %:search% or r.title like %:search%")
    List<Recipe> findRecipeBySearchText(@Param("search") String search);

    @Query("select distinct r from Recipe r where r.owner = :search")
    List<Recipe> findRecipeByUser(@Param("search") User user);
}
