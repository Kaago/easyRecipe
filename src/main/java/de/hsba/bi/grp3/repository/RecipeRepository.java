package de.hsba.bi.grp3.repository;


import de.hsba.bi.grp3.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {


}
