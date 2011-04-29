package php.ware.ui.quickfix;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import php.ware.validation.WebJavaValidator;
import php.ware.web.AbstractAction;
import php.ware.web.Controller;
import php.ware.web.CrudAction;
import php.ware.web.GenericAction;
import php.ware.web.WebFactory;

public class WebQuickfixProvider extends DefaultQuickfixProvider {

    @Fix(WebJavaValidator.INVALID_NAME)
    public void capitalizeName(final Issue issue,
            IssueResolutionAcceptor acceptor) {
        acceptor.accept(issue, "Capitalize name", "Capitalize the name.",
                "upcase.png", new IModification() {
                    @Override
                    public void apply(IModificationContext context)
                            throws BadLocationException {
                        IXtextDocument xtextDocument = context
                                .getXtextDocument();
                        String firstLetter = xtextDocument.get(
                                issue.getOffset(), 1);
                        xtextDocument.replace(issue.getOffset(), 1,
                                firstLetter.toUpperCase());
                    }
                });
    }

    @Fix(WebJavaValidator.EMPTY_CRUD_ACTION)
    public void replaceForGenericAction(final Issue issue,
            IssueResolutionAcceptor acceptor) {
        acceptor.accept(issue, "Convert to generic action",
                "Convert to generic action.", "upcase.png",
                new ISemanticModification() {
                    @Override
                    public void apply(EObject element,
                            IModificationContext context) throws Exception {
                        createGenericActionFromCrudAction((CrudAction) element);
                    }
                });
    }

    protected boolean createGenericActionFromCrudAction(CrudAction action) {
        GenericAction newAction = WebFactory.eINSTANCE.createGenericAction();
        newAction.setName(action.getName());
        return replaceAction(action, newAction);
    }

    protected boolean replaceAction(CrudAction action, GenericAction newAction) {
        EObject container = action.eContainer();
        EList<AbstractAction> elements = ((Controller) container).getActions();
        int index = elements.indexOf(action) + 1;
        elements.add(index, newAction);
        elements.remove(action);
        return true;
    }
}
