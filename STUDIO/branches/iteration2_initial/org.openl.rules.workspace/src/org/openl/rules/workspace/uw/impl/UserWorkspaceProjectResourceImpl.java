package org.openl.rules.workspace.uw.impl;

import java.io.InputStream;

import org.acegisecurity.annotation.Secured;
import org.openl.rules.security.Privileges;
import org.openl.rules.workspace.abstracts.ProjectException;
import org.openl.rules.workspace.abstracts.ProjectResource;
import org.openl.rules.workspace.dtr.RepositoryProjectResource;
import org.openl.rules.workspace.lw.LocalProjectResource;
import org.openl.rules.workspace.uw.UserWorkspaceProjectArtefact;
import org.openl.rules.workspace.uw.UserWorkspaceProjectResource;

public class UserWorkspaceProjectResourceImpl extends UserWorkspaceProjectArtefactImpl implements UserWorkspaceProjectResource{
    private LocalProjectResource localResource;
    private RepositoryProjectResource dtrResource;
    
    protected UserWorkspaceProjectResourceImpl(UserWorkspaceProjectImpl project, LocalProjectResource localResource, RepositoryProjectResource dtrResource) {
        super(project, localResource, dtrResource);
        
        updateArtefact(localResource, dtrResource);
    }

	@Secured (Privileges.PRIVILEGE_EDIT)
    public void setContent(InputStream inputStream) throws ProjectException {
        if (isReadOnly()) {
            throw new ProjectException("Cannot update content in read only mode!", null);
        }
        
        localResource.setContent(inputStream);
    }

    public InputStream getContent() throws ProjectException {
        return getResource().getContent();
    }

    public String getResourceType() {
        return getResource().getResourceType();
    }

    public UserWorkspaceProjectArtefact getArtefact(String name) throws ProjectException {
        throw new ProjectException("Cannot find project artefact ''{0}''", null, name);
    }
    
	@Secured (Privileges.PRIVILEGE_EDIT)
    public void delete() throws ProjectException {
        if (isReadOnly()) {
            throw new ProjectException("Cannot delete in read only mode!", null);
        }

        localResource.remove();
    }
    
    public boolean isFolder() {
        return false;
    }

    public boolean hasArtefact(String name) {
        return false;
    }

    // --- protected
    
    protected void updateArtefact(LocalProjectResource localResource, RepositoryProjectResource dtrResource) {
        super.updateArtefact(localResource, dtrResource);

        this.localResource = localResource;
        this.dtrResource = dtrResource;
    }
    
    protected ProjectResource getResource() {
        return (isLocal() ? localResource : dtrResource);
    }
}
