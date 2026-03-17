package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
public class Assets {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer assetId;
    @Column(nullable = false)
    private String item;
    @Column(nullable = false)
    private Boolean isMobile;
    @Column(nullable = false)
    private String serialNumber;
    private String specifications;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Categories categoryId;
    private Boolean isRemoved;

    public Assets(Integer assetId, String item, Boolean mobile, String serialNumber, String specifications, Categories categoryId, Boolean isRemoved) {
        this.assetId = assetId;
        this.item = item;
        this.isMobile = mobile;
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.categoryId = categoryId;
        this.isRemoved = isRemoved;
    }

    public Assets(){

    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }
}
