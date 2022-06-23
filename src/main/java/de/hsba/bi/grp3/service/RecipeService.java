package de.hsba.bi.grp3.service;


import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe createRecipe(String title){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);

        return repository.save(recipe);
    }

    public void CreateIngredient() {

    }

    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public Recipe getJournal(Long id) { return repository.findById(id).orElse(null); }


    public Collection<Recipe> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
