package com.myresourcer.MyResourcer.DTOs;


public class DTO_Comments {
    private Integer commentId;
    private  String type;
    private  String comment;
    private String DateTime;
    private Integer assetId;
    private Integer userId;

    public DTO_Comments(Integer commentId, String type, String comment, String dateTime, Integer asset, Integer user) {
        this.commentId = commentId;
        this.type = type;
        this.comment = comment;
        DateTime = dateTime;
        this.assetId = asset;
        this.userId = user;
    }

    public DTO_Comments(){
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
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
}
