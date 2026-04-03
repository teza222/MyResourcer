package com.myresourcer.MyResourcer.DTOs;

import com.myresourcer.MyResourcer.Models.Categories;
import jakarta.persistence.*;

public class DTO_Assets {
    private Integer assetId;
    private String item;
    private Boolean mobile; // Renamed from isMobile to mobile for consistency with JSON
    private String serialNumber;
    private String specifications;
    private Integer categoryId;
    private Boolean removed; // Renamed from isRemoved to removed for consistency with JSON


    public DTO_Assets(Integer assetId, String item, Boolean mobile, String serialNumber, String specifications, Integer categoryId, Boolean removed) {
        this.assetId = assetId;
        this.item = item;
        this.mobile = mobile;
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.categoryId = categoryId;
        this.removed = removed;
    }

    public DTO_Assets(){

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

    // Changed from isMobile() to getMobile() for standard JavaBean convention
    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    // Changed from getRemoved() to getRemoved() for standard JavaBean convention
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }
}
