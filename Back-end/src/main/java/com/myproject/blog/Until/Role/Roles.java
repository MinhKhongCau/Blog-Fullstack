package com.myproject.blog.Until.Role;

public enum Roles {
    USER(1), ADMIN(2),EDITOR(3);

    private Integer role_id;

    private Roles(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getRole() {
        return role_id;
    }
}
