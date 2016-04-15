package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.forms.CommentCreationForm;

public class CreateCommentCommand extends Command {

    private CommentCreationForm form;

    /**
     * Command to create Comments on BugReport.
     * @param bugTrap BugTrap system.
     * @param form CreateCommentCommand.
     */
    public CreateCommentCommand(BugTrap bugTrap, CommentCreationForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getBugReportManager().addComment(form.getCommentable(), form.getText());
    }
}
