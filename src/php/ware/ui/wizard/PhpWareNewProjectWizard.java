package php.ware.ui.wizard;

import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import org.eclipse.xtext.ui.wizard.IProjectInfo;

import com.google.inject.Inject;

public class PhpWareNewProjectWizard extends WebNewProjectWizard {

    private WizardNewProjectCreationPage mainPage;
    private CustomSettingsPage settingsPage;

    @Inject
    public PhpWareNewProjectWizard(IProjectCreator projectCreator) {
        super(projectCreator);
        setWindowTitle("New phpWare Project");
    }

    /**
     * Use this method to add pages to the wizard. The one-time generated
     * version of this class will add a default new project page to the wizard.
     */
    @Override
    public void addPages() {
        mainPage = new WizardNewProjectCreationPage("basicNewProjectPage");
        mainPage.setTitle("phpWare Project");
        mainPage.setDescription("Create a new phpWare project.");
        addPage(mainPage);

        settingsPage = new CustomSettingsPage("settingsProjectPage");
        settingsPage.setTitle("phpWare Project Settings");
        settingsPage
                .setDescription("Select features you want to ativate in your project");
        addPage(settingsPage);
    }

    /**
     * Use this method to read the project settings from the wizard pages and
     * feed them into the project info class.
     */
    @Override
    protected IProjectInfo getProjectInfo() {
        php.ware.ui.wizard.WebProjectInfo projectInfo = new php.ware.ui.wizard.WebProjectInfo();
        projectInfo.setProjectName(mainPage.getProjectName());
        projectInfo.setPersistenceCartridge(settingsPage
                .getSelectedPersistenceCartridge());
        projectInfo.setControllerCartridge(settingsPage
                .getSelectedControllerCartridge());
        return projectInfo;
    }
}
