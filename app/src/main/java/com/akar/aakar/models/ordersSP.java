package com.akar.aakar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ordersSP {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sp_id")
    @Expose
    private Integer spId;
    @SerializedName("sr_id")
    @Expose
    private Integer srId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("spname")
    @Expose
    private String spname;
    @SerializedName("srname")
    @Expose
    private String srname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ordersSP withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public ordersSP withSpId(Integer spId) {
        this.spId = spId;
        return this;
    }

    public Integer getSrId() {
        return srId;
    }

    public void setSrId(Integer srId) {
        this.srId = srId;
    }

    public ordersSP withSrId(Integer srId) {
        this.srId = srId;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ordersSP withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ordersSP withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ordersSP withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ordersSP withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ordersSP withOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public ordersSP withSpname(String spname) {
        this.spname = spname;
        return this;
    }

    public String getSrname() {
        return srname;
    }

    public void setSrname(String srname) {
        this.srname = srname;
    }

    public ordersSP withSrname(String srname) {
        this.srname = srname;
        return this;
    }

}