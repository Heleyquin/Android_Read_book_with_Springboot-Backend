package com.example.docsach.Model.DTO;

import java.io.Serializable;
import java.util.List;

public class PurchaseRequest implements Serializable {
    private List<LichSuMuaRequest> lichSuMuaRequests;
    private List<Long> wishIds;

    public PurchaseRequest() {
    }

    public PurchaseRequest(List<LichSuMuaRequest> lichSuMuaRequests, List<Long> wishIds) {
        this.lichSuMuaRequests = lichSuMuaRequests;
        this.wishIds = wishIds;
    }

    public List<LichSuMuaRequest> getLichSuMuaRequests() {
        return lichSuMuaRequests;
    }

    public void setLichSuMuaRequests(List<LichSuMuaRequest> lichSuMuaRequests) {
        this.lichSuMuaRequests = lichSuMuaRequests;
    }

    public List<Long> getWishIds() {
        return wishIds;
    }

    public void setWishIds(List<Long> wishIds) {
        this.wishIds = wishIds;
    }
}
