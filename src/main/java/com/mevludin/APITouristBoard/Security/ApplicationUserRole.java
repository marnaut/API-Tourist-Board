package com.mevludin.APITouristBoard.Security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mevludin.APITouristBoard.Security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(EDITOR_READ,EDITOR_WRITE,VISITOR_READ,VISITOR_WRITE)),
    EDITOR(Sets.newHashSet(VISITOR_READ,VISITOR_WRITE,EDITOR_WRITE,EDITOR_READ)),
    VISITOR(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
