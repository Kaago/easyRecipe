package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select distinct r from Recipe r join r.ingredients i where i.name like %:search% or r.title like %:search%")
    List<Recipe> findRecipeBySearchText(@Param("search") String search);
}
