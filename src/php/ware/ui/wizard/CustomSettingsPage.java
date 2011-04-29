/*
 * 
 */
package php.ware.ui.wizard;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * Wizard page shown when the user has chosen car as means of transport
 */
public class CustomSettingsPage extends WizardPage implements Listener {
    String[] persistenceCartridges = { "None", "CI Active Record",
            "Zend Active Record", "Easy PHP Data Object (EZPDO)" };
    String[] presentationCartridges = { "None", "CI Front Controller",
            "Zend Front Controller" };
    String[] profiles = { "None", "Code Igniter", "Zend Framework" };

    Button profileButton;
    Combo profileCombo;
    Combo persistenceCombo;
    Combo presentationCombo;
    Group cartridgesGroup;

    /**
     * Constructor
     * 
     * @param arg0
     */
    public CustomSettingsPage(String arg0) {
        super(arg0);
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    @Override
    public void createControl(Composite parent) {
        // create the composite to hold the widgets
        Composite composite = new Composite(parent, SWT.NONE);

        // create the desired layout for this wizard page
        GridLayout gl = new GridLayout();
        int ncol = 3;
        int nrow = 5;
        gl.numColumns = ncol;
        composite.setLayout(gl);

        // create the widgets. If the appearance of the widget is different from
        // the default,
        // create a GridData for it to set the alignment and define how much
        // space it will occupy

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = ncol;

        createProfileSection(composite, gd);

        createLine(composite, ncol);
        createSpace(composite, ncol, nrow);

        createPersistenceSection(composite, gd);

        createSpace(composite, ncol, nrow);

        createPresentationSection(composite, gd);

        // set the composite as the control for this page
        setControl(composite);
    }

    /*
     * Process the events: when the user has entered all information the wizard
     * can be finished
     */
    @Override
    public void handleEvent(Event e) {

        String id = e.widget.getData("ID").toString();

        if (id.equals(profileButton.getData("ID").toString())) {
            profileCombo.setEnabled(profileButton.getSelection());
        }

        if (id.equals(profileButton.getData("ID").toString())
                && !profileButton.getSelection()) {
            profileCombo.select(0);
            persistenceCombo.select(0);
            presentationCombo.select(0);
            profileCombo.setEnabled(false);
        }

        if (id.equals(profileCombo.getData("ID").toString())) {
            persistenceCombo.select(profileCombo.getSelectionIndex());
            presentationCombo.select(profileCombo.getSelectionIndex());
        }

        setPageComplete(isPageComplete());
        getWizard().getContainer().updateButtons();
    }

    @Override
    public boolean canFlipToNextPage() {
        // no next page for this path through the wizard
        return false;
    }

    /*
     * Sets the completed field on the wizard class when all the information is
     * entered and the wizard can be completed
     */
    @Override
    public boolean isPageComplete() {
        return persistenceCombo.getSelectionIndex() != 0
                || presentationCombo.getSelectionIndex() != 0;
    }

    private void createLine(Composite parent, int ncol) {
        Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
                | SWT.BOLD);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        line.setLayoutData(gridData);
    }

    private void createSpace(Composite parent, int ncol, int nrow) {
        Label line = new Label(parent, SWT.HORIZONTAL);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = ncol;
        gridData.verticalSpan = nrow;
        line.setLayoutData(gridData);
    }

    private void createProfileSection(Composite composite, GridData gd) {
        // profiles button
        profileButton = new Button(composite, SWT.CHECK);
        profileButton.setText("Use Main Configuration Profile");
        profileButton.setLayoutData(gd);
        profileButton.setSelection(false);
        profileButton.setData("ID", "PROFILE_BTN");
        profileButton.addListener(SWT.Selection, this);

        profileCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
        profileCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        profileCombo.setItems(profiles);
        profileCombo.select(0);
        profileCombo.setEnabled(false);
        profileCombo.setData("ID", "PROFILE_CMB");
        profileCombo.addListener(SWT.Selection, this);
    }

    private void createPersistenceSection(Composite composite, GridData gd) {
        // persistence cartridge selection
        new Label(composite, SWT.NONE).setText("Persistence Cartridge");
        persistenceCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
        persistenceCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        persistenceCombo.setItems(persistenceCartridges);
        persistenceCombo.select(3); // default to EZPDO
        persistenceCombo.setData("ID", "PERSISTENCE_CMB");
        persistenceCombo.addListener(SWT.Selection, this);
    }

    private void createPresentationSection(Composite composite, GridData gd) {
        new Label(composite, SWT.NONE).setText("Controller Cartridge");
        presentationCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
        presentationCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        presentationCombo.setItems(presentationCartridges);
        presentationCombo.select(0);
        presentationCombo.setData("ID", "PRESENTATION_CMB");
        presentationCombo.addListener(SWT.Selection, this);
    }

    public int getSelectedControllerCartridge() {
        return this.presentationCombo.getSelectionIndex();
    }

    public int getSelectedPersistenceCartridge() {
        return this.persistenceCombo.getSelectionIndex();
    }

}
