package com.example.docsach.Model.DTO;

import java.io.Serializable;

public class VNPay_Response implements Serializable {
    private Long id_sach;
    private String status;
    private String message;
    private String url;

    public VNPay_Response() {
    }

    public VNPay_Response(Long id_sach, String status, String message, String url) {
        this.id_sach = id_sach;
        this.status = status;
        this.message = message;
        this.url = url;
    }

    public Long getId_sach() {
        return id_sach;
    }

    public void setId_sach(Long id_sach) {
        this.id_sach = id_sach;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
