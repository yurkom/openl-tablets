package org.openl.rules.ui.tablewizard;

import org.apache.commons.lang.StringUtils;
import org.openl.rules.ui.WebStudio;
import org.openl.rules.ui.copy.NewVersionTableCopier;
import org.openl.rules.ui.copy.TableNamesCopier;
import org.openl.rules.ui.copy.TablePropertyCopier;
import org.openl.rules.web.jsf.util.FacesUtils;
import org.openl.rules.webstudio.web.util.Constants;
import org.openl.rules.webstudio.web.util.WebStudioUtils;

public class TableCopierWizardManager extends TableWizard {    

    static enum CopyType {
        CHANGE_NAMES,
        CHANGE_PROPERTIES,
        CHANGE_VERSION
    }    
    
    public String elementUri;
    
    private CopyType copyType = CopyType.CHANGE_NAMES;
    
    public TableCopierWizardManager () {
        init();
    }
    
    public String getElementUri() {
        return elementUri;
    }

    public void setElementUri(String elementUri) {
        this.elementUri = elementUri;
    }    
    
    public String getCopyType() {
        return copyType.name();
    }
    
    public void setCopyType(String copyType) {
        try {
            this.copyType = CopyType.valueOf(copyType);
        } catch (IllegalArgumentException e) {
            this.copyType = CopyType.CHANGE_NAMES;
        }
    }

    @Override
    public String startWizard() {
        reload();
        switch (copyType) {
            case CHANGE_NAMES:
                wizard = new TableNamesCopier(elementUri); 
                break;
            case CHANGE_PROPERTIES:
                wizard = new TablePropertyCopier(elementUri);
                break;
            case CHANGE_VERSION:
                wizard = new NewVersionTableCopier(elementUri);
                break;
            default:
                return null;
        }
        wizard.next();
        return wizard.getName();
    }

    private void reload() {
        elementUri = null;
        init();        
    }

    @Override
    public String cancel() {
        if (wizard != null) {
            wizard.cancel();
        }
        return "newTableCancel";
    }

    private void init() {
        elementUri = FacesUtils.getRequestParameter(Constants.REQUEST_PARAM_URI);
        WebStudio studio = WebStudioUtils.getWebStudio();
        if (StringUtils.isBlank(elementUri)) {
            elementUri = studio.getTableUri();
        } 
    }

    @Override
    public String start() {
        copyType = CopyType.CHANGE_NAMES;
        return "wizardSelect";       
    }

}
