package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Order All Recipes by the highest average Comment Rating
    @Query("SELECT r FROM Recipe r LEFT JOIN r.comments c GROUP BY r ORDER BY AVG(c.rating) DESC")
    List<Recipe> findAllRecipeOrderByRatingDesc(@Param("search") String search);

    // Order Recipes by a user given search text and the highest average Comment Rating
    @Query("SELECT r FROM Recipe r LEFT JOIN r.comments c JOIN r.ingredients i where i.name like %:search% or r.title like %:search% GROUP BY r ORDER BY AVG(c.rating) DESC")
    List<Recipe> findRecipeBySearchTextOrderByRatingDesc(@Param("search") String search);

    // Order All Recipes by the lowest average Comment Rating
    @Query("SELECT r FROM Recipe r LEFT JOIN r.comments c GROUP BY r ORDER BY AVG(c.rating) ASC")
    List<Recipe> findAllRecipeOrderByRatingAsc(@Param("search") String search);

    // Order Recipes by a user given search text and the lowest average Comment Rating
    @Query("SELECT r FROM Recipe r LEFT JOIN r.comments c JOIN r.ingredients i where i.name like %:search% or r.title like %:search% GROUP BY r ORDER BY AVG(c.rating) ASC")
    List<Recipe> findRecipeBySearchTextOrderByRatingAsc(@Param("search") String search);


    @Query("select distinct r from Recipe r where r.owner = :search")
    List<Recipe> findRecipeByUser(@Param("search") User user);
}
