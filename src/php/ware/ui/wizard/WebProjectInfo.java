package php.ware.ui.wizard;

import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

public class WebProjectInfo extends DefaultProjectInfo {
    private int persistenceCartridge;
    private int controllerCartridge;

    public int getPersistenceCartridge() {
        return persistenceCartridge;
    }

    public void setPersistenceCartridge(int persistenceCartridge) {
        this.persistenceCartridge = persistenceCartridge;
    }

    public int getControllerCartridge() {
        return controllerCartridge;
    }

    public void setControllerCartridge(int controllerCartridge) {
        this.controllerCartridge = controllerCartridge;
    }

    public String getPersistenceCartridgeName() {
        String result;
        switch (persistenceCartridge) {
        case 1:
            result = "ci";
            break;

        case 2:
            result = "zf";
            break;
        case 3:
            result = "ezpdo";
            break;

        default: // 0 nothing to do
            result = "";
            break;
        }
        return result;
    }

    public String getControllerCartridgeName() {
        String result;
        switch (controllerCartridge) {
        case 1:
            result = "ci";
            break;

        case 2:
            result = "zf";
            break;

        default: // 0 nothing to do
            result = "";
            break;
        }
        return result;
    }
}
