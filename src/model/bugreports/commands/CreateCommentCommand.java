package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.comments.Commentable;
import model.bugreports.forms.CommentCreationForm;

public class CreateCommentCommand extends Command {

    private CommentCreationForm form;

    public CreateCommentCommand(BugTrap bugTrap, CommentCreationForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((Commentable) form.getCommentable()).addComment(form.getText());
    }
}
