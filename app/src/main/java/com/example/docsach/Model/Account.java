package com.example.docsach.Model;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private boolean active;
    private int role;

    public Account() {
    }

    public Account(int role, boolean active, String mk, String tk) {
        this.role = role;
        this.active = active;
        this.password = mk;
        this.username = tk;
    }

    public String getTk() {
        return username;
    }

    public void setTk(String tk) {
        this.password = tk;
    }

    public String getMk() {
        return password;
    }

    public void setMk(String mk) {
        this.password = mk;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static final class AccountBuilder {
        private Account account;

        public AccountBuilder() {
            account = new Account();
        }

        public AccountBuilder(Account other) {
            this.account = other;
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder withTk(String tk) {
            account.setTk(tk);
            return this;
        }

        public AccountBuilder withMk(String mk) {
            account.setMk(mk);
            return this;
        }

        public AccountBuilder withActive(boolean active) {
            account.setActive(active);
            return this;
        }

        public AccountBuilder withRole(int role) {
            account.setRole(role);
            return this;
        }

        public Account build() {
            return account;
        }
    }
}