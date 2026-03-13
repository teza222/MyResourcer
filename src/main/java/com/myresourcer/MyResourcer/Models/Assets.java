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
    private Boolean mobile;
    @Column(nullable = false)
    private String serialNumber;
    private String specifications;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Categories categoryId;

    public Assets(Integer assetId, String item, Boolean mobile, String serialNumber, String specifications, Categories categoryId) {
        this.assetId = assetId;
        this.item = item;
        this.mobile = mobile;
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.categoryId = categoryId;
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

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }
}



//    // This helper method tells Jackson how to handle a numeric ID for the category.
//    @JsonProperty("categoryId")
//    public void setCategoryById(Integer id) {
//        if (id != null) {
//            this.categoryId = new Categories();
//            this.categoryId.setCategoryId(id);
//        }
//    }
//}
