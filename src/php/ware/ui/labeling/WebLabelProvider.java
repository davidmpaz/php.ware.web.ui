/*
 * generated by Xtext
 */
package php.ware.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.eclipse.xtext.ui.label.StylerFactory;

import php.ware.web.Controller;
import php.ware.web.CrudAction;
import php.ware.web.GenericAction;
import php.ware.web.Import;
import php.ware.web.Module;
import php.ware.web.PackageDeclaration;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class WebLabelProvider extends DefaultEObjectLabelProvider {

    @Inject
    public WebLabelProvider(AdapterFactoryLabelProvider delegate) {
        super(delegate);
    }

    public StyledString text(Import ele) {
        return new StyledString(ele.getImportedNamespace(),
                StyledString.QUALIFIER_STYLER);
    }

    public String image(Import ele) {
        return ele.eClass().getName() + ".png";
    }

    public StyledString text(PackageDeclaration ele) {
        return new StyledString(ele.getName(), StyledString.QUALIFIER_STYLER);
    }

    public String image(PackageDeclaration ele) {
        return ele.eClass().getName() + ".png";
    }

    public StyledString text(Module ele) {
        return new StyledString(ele.getName(), StyledString.QUALIFIER_STYLER);
    }

    public String image(Module ele) {
        return ele.eClass().getName() + ".png";
    }

    public StyledString text(Controller ele) {
        return new StyledString(ele.getName(), StyledString.COUNTER_STYLER);
    }

    public String image(Controller ele) {
        return ele.eClass().getName() + ".png";
    }

    public StyledString text(CrudAction ele) {
        StyledString ss = new StyledString();
        boolean notFeatured = ele.getExcludedFeatures().size() == 0
                && ele.getIncludedFeatures().size() == 0;

        if (notFeatured && ele.getTemplate() == null
                && ele.getNavigatedAction() == null)
            ss.append(ele.getName() + " ", getEmptyActionStyler());
        else if (notFeatured)
            ss.append(ele.getName() + " ", getEmptyIcludesExcludesStyler());
        else
            ss.append(ele.getName() + " ");

        ss.append(ele.getOperation().getLiteral() + " ",
                StyledString.DECORATIONS_STYLER).append(
                ele.getType().getName(), StyledString.QUALIFIER_STYLER);
        if (ele.isMulti()) {
            ss.append("[]", StyledString.QUALIFIER_STYLER);
        }
        return ss;
    }

    public String image(CrudAction ele) {
        return ele.eClass().getName() + ".png";
    }

    public StyledString text(GenericAction ele) {
        StyledString ss = new StyledString();
        ss.append(ele.getName() + " ").append("Action",
                StyledString.QUALIFIER_STYLER);
        return ss;
    }

    public String image(GenericAction ele) {
        return ele.eClass().getName() + ".png";
    }

    protected Styler getEmptyIcludesExcludesStyler() {

        StylerFactory sf = new StylerFactory();
        TextStyle ts = new TextStyle();

        ts.setStyle(SWT.NORMAL);
        ts.setColor(new RGB(207, 207, 207));
        ts.setBackgroundColor(new RGB(255, 255, 255));

        return sf.createXtextStyleAdapterStyler(ts);
    }

    protected Styler getEmptyActionStyler() {

        StylerFactory sf = new StylerFactory();
        TextStyle ts = new TextStyle();

        ts.setStyle(SWT.NORMAL);
        ts.setColor(new RGB(200, 0, 0));
        ts.setBackgroundColor(new RGB(255, 255, 255));

        return sf.createXtextStyleAdapterStyler(ts);
    }
}
