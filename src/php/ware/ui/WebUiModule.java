/*
 * generated by Xtext
 */
package php.ware.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.wizard.IProjectCreator;

import php.ware.ui.wizard.PhpWareProjectCreator;

/**
 * Use this class to register components to be used within the IDE.
 */
public class WebUiModule extends php.ware.ui.AbstractWebUiModule {
    public WebUiModule(AbstractUIPlugin plugin) {
        super(plugin);
    }

    /**
     * Project creator for wizard
     */
    @Override
    public Class<? extends IProjectCreator> bindIProjectCreator() {
        return PhpWareProjectCreator.class;
    }
}
