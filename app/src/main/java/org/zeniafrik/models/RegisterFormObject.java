package org.zeniafrik.models;

public class RegisterFormObject {
    private final String name, email, password, phone;

    public RegisterFormObject(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
