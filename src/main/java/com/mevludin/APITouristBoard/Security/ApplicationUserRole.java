package com.mevludin.APITouristBoard.Security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mevludin.APITouristBoard.Security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(EDITOR_READ,EDITOR_WRITE)),
    EDITOR(Sets.newHashSet(EDITOR_WRITE,EDITOR_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
