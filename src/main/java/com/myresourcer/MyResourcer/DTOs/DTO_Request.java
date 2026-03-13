package com.myresourcer.MyResourcer.DTOs;

public class DTO_Request {
    private Integer requestId;
    private Integer assetId;
    private Integer userId;
    private Integer statusId;
    private Integer conditionId;
    private String dateOut;
    private String dateIn;
    private String timeOut;
    private String timeIn;

    public DTO_Request(Integer requestId, Integer assetId, Integer userId, Integer statusId, Integer conditionId, String dateOut, String dateIn, String timeOut, String timeIn) {
        this.requestId = requestId;
        this.assetId = assetId;
        this.userId = userId;
        this.statusId = statusId;
        this.conditionId = conditionId;
        this.dateOut = dateOut;
        this.dateIn = dateIn;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }

    public DTO_Request(){

    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }
}
