package org.zeniafrik.models;

import android.support.annotation.Nullable;

public class User {
    protected final int id;
    private String account_status, account_type;
    private String name, email, phone, address;

    public User(int id, String account_status, String name, String email, String phone, @Nullable String address, String account_type) {
        this.id = id;
        this.account_status = account_status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.account_type = account_type;
    }

    public User(int id, String account_type) {
        this.id = id;
        this.account_type = account_type;
    }

    public String getAccountStatus() {
        return account_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_type() {
        return account_type;
    }

}
