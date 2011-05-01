package php.ware.ui.wizard;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xpand2.XpandExecutionContextImpl;
import org.eclipse.xpand2.XpandFacade;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.OutputImpl;
import org.eclipse.xtend.type.impl.java.JavaBeansMetaModel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class PhpWareProjectCreator extends WebProjectCreator {

    protected static final String DSL_GENERATOR_PROJECT_NAME = "php.ware.web";

    protected static final String SRC_ROOT = "src";
    protected static final String SRC_GEN_ROOT = "src-gen";
    protected final List<String> SRC_FOLDER_LIST = ImmutableList.of(SRC_ROOT,
            SRC_GEN_ROOT);

    @Override
    protected WebProjectInfo getProjectInfo() {
        return super.getProjectInfo();
    }

    @Override
    protected String getModelFolderName() {
        return SRC_ROOT;
    }

    @Override
    protected List<String> getAllFolders() {
        return SRC_FOLDER_LIST;
    }

    @Override
    protected List<String> getRequiredBundles() {
        /*
         * Override from AbstractPluginProjectCreator since THIS super class
         * include also the "php.ware.web.generator" project name which doesn't
         * exist no more
         */
        List<String> result = Lists.newArrayList("com.ibm.icu",
                "org.eclipse.xtext", "org.eclipse.xtext.generator",
                "org.eclipse.xtend", "org.eclipse.xtend.typesystem.emf",
                "org.eclipse.xpand",
                "de.itemis.xtext.antlr;resolution:=optional",
                "org.eclipse.xtend.util.stdlib",
                "org.eclipse.emf.mwe2.launch;resolution:=optional");

        // Main DSL projects
        result.add("php.ware.entity");
        result.add("php.ware.web");
        result.add("php.ware.config");
        // get bundles based on user selection
        WebProjectInfo pi = this.getProjectInfo();

        String cartridgeName = pi.getPersistenceCartridgeName();
        if (cartridgeName != "") {
            result.add("php.ware.entity." + cartridgeName);
        }

        cartridgeName = pi.getControllerCartridgeName();
        if (cartridgeName != "") {
            result.add("php.ware.web." + cartridgeName);
        }

        return result;
    }

    @Override
    protected void enhanceProject(final IProject project,
            final IProgressMonitor monitor) throws CoreException {
        OutputImpl output = new OutputImpl();
        output.addOutlet(new Outlet(false, getEncoding(), null, true, project
                .getLocation().makeAbsolute().toOSString()));

        XpandExecutionContextImpl execCtx = new XpandExecutionContextImpl(
                output, null);
        execCtx.getResourceManager().setFileEncoding("UTF-8");
        execCtx.registerMetaModel(new JavaBeansMetaModel());

        XpandFacade facade = XpandFacade.create(execCtx);
        facade.evaluate("php::ware::ui::wizard::WebNewProject::main",
                getProjectInfo());

        project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
    }

}