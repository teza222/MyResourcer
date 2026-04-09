package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
public class Comments {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer commentId;
    @Column(nullable = false)
    private  String type;
    @Column(nullable = false)
    private  String comment;
    @Column(nullable = false)
    private String DateTime;
    @ManyToOne
    @JoinColumn(name = "assetId", nullable = false)
    private  Assets asset;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private  Users user;

    public Comments(Integer commentId, String type, String comment, String dateTime, Assets asset, Users user) {
        this.commentId = commentId;
        this.type = type;
        this.comment = comment;
        DateTime = dateTime;
        this.asset = asset;
        this.user = user;
    }

    public Comments(){
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    public String getDateTime() {
        return DateTime;
    }
    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
    public Assets getAsset() {
        return asset;
    }

    public void setAsset(Assets asset) {
        this.asset = asset;
    }
}
