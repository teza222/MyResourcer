package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
public class Request {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer requestId;
    @ManyToOne
    @JoinColumn(name = "assetId", nullable = false)
    private Assets assetId;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Users userId;
    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status statusId;
    @ManyToOne
    @JoinColumn(name = "conditionId", nullable = false)
    private Condition conditionId;
    private String dateOut;
    private String dateIn;
    private String timeOut;
    private String timeIn;

    public Request(Integer requestId, Assets assetId, Users userId, Status statusId, Condition conditionId, String dateOut, String dateIn, String timeOut, String timeIn) {
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

    public Request(){

    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }


    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public Condition getConditionId() {
        return conditionId;
    }

    public void setConditionId(Condition conditionId) {
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
    public Assets getAssetId() {
        return assetId;
    }
    public void setAssetId(Assets assetId) {
        this.assetId = assetId;
    }
}
