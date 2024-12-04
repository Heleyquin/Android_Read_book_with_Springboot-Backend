package com.example.docsach.Model.DTO;

import java.io.Serializable;

public class AccountRequest implements Serializable {
    private String tk;
    private String mk;

    public AccountRequest() {
    }

    public AccountRequest(String tk, String mk) {
        this.tk = tk;
        this.mk = mk;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }
}
