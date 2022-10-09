package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Order All Recipes by the highest average Comment Rating
    @Query("SELECT r FROM Recipe r JOIN r.comments c WHERE r.isPrivat = false GROUP BY r ORDER BY AVG(c.rating) DESC")
    List<Recipe> findAllRecipeOrderByRatingDesc();

    // Order Recipes by a user given search text and the highest average Comment Rating
    @Query("SELECT r FROM Recipe r JOIN r.comments c JOIN r.ingredients i WHERE lower(i.name) LIKE lower(concat('%', :search,'%')) OR lower(r.title) LIKE lower(concat('%', :search,'%')) AND r.isPrivat = false GROUP BY r ORDER BY AVG(c.rating) DESC")
    List<Recipe> findRecipeBySearchTextOrderByRatingDesc(@Param("search") String search);

    // Order All Recipes by the lowest average Comment Rating
    @Query("SELECT r FROM Recipe r JOIN r.comments c  WHERE r.isPrivat = false GROUP BY r ORDER BY AVG(c.rating) ASC")
    List<Recipe> findAllRecipeOrderByRatingAsc();

    // Order Recipes by a user given search text and the lowest average Comment Rating
    @Query("SELECT r FROM Recipe r JOIN r.comments c JOIN r.ingredients i WHERE lower(i.name) LIKE lower(concat('%', :search,'%')) OR lower(r.title) LIKE lower(concat('%', :search,'%')) AND r.isPrivat = false GROUP BY r ORDER BY AVG(c.rating) ASC")
    List<Recipe> findRecipeBySearchTextOrderByRatingAsc(@Param("search") String search);

    // Order all Recipes by a search text
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE lower(i.name) LIKE lower(concat('%', :search,'%')) OR lower(r.title) LIKE lower(concat('%', :search,'%')) AND r.isPrivat = false GROUP BY r")
    List<Recipe> findAllPublicRecipeBySearchText(@Param("search") String search);

    @Query("SELECT DISTINCT r FROM Recipe r WHERE r.owner = :search")
    List<Recipe> findRecipeByUser(@Param("search") User user);

    @Query("SELECT DISTINCT r FROM Recipe r WHERE r.isPrivat = false")
    List<Recipe> findAllPublicRecipe();
}
