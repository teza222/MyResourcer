package com.myresourcer.MyResourcer.DTOs;

import com.myresourcer.MyResourcer.Models.Categories;
import jakarta.persistence.*;

public class DTO_Assets {
    private Integer assetId;
    private String item;
    private Boolean mobile;
    private String serialNumber;
    private String specifications;
    private Integer categoryId;

    public DTO_Assets(Integer assetId, String item, Boolean mobile, String serialNumber, String specifications, Integer categoryId) {
        this.assetId = assetId;
        this.item = item;
        this.mobile = mobile;
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.categoryId = categoryId;
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

    public Boolean isMobile() {
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
}



