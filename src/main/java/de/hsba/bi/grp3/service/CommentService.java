package de.hsba.bi.grp3.service;

import de.hsba.bi.grp3.comment.Comment;
import de.hsba.bi.grp3.recipe.Recipe;
import de.hsba.bi.grp3.repository.CommentRepository;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment createComment(String textContent, float rating, User owner, Recipe recipe){
        Comment comment = new Comment(textContent, rating, owner, recipe);
        return saveComment(comment);
    }

    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    public void updateComment(Comment comment) {
       repository.save(comment);
    }


    public List<Comment> getAllRecipeComments(Recipe recipe) {
        List<Comment> test = repository.findRecipeComments(recipe);
        return test;
    }

    public Comment getComment(Long id) { return repository.findById(id).orElse(null); }

    public List<Comment> findUserComments(User user){
        return  repository.findUserComments(user);
    }
}
