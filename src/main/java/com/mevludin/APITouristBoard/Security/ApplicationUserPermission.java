package com.mevludin.APITouristBoard.Security;

public enum ApplicationUserPermission {
    EDITOR_READ("editor:read"),
    EDITOR_WRITE("editor:write"),
    VISITOR_READ("visitor:read"),
    VISITOR_WRITE("visitor:write");


    private final  String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
