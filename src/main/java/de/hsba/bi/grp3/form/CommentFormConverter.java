package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.comment.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentFormConverter {

    public CommentForm toForm(Comment comment) {
        CommentForm commentForm = new CommentForm();
        commentForm.setTextContent(comment.getTextContent());
        commentForm.setRating(comment.getRating());
        commentForm.setOwner(comment.getOwner());
        commentForm.setRecipe(comment.getRecipe());
        return commentForm;
    }

    public Comment update(Comment comment, CommentForm commentForm) {
        comment.setTextContent(commentForm.getTextContent());
        comment.setRating(commentForm.getRating());
        return comment;
    }

    public Comment createComment(Comment comment, CommentForm commentForm){
        comment.setTextContent(commentForm.getTextContent());
        comment.setRating(commentForm.getRating());
        return comment;
    }
}
