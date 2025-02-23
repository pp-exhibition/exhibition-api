package com.pp.enums;

public enum Role {
    ADMIN, USER
    ;

    public String getSecurityRole() {
        return "ROLE_" + this;
    }
}
