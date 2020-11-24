package com.akar.aakar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ordersSR {

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
    @SerializedName("receiver_city")
    @Expose
    private String receiverCity;
    @SerializedName("receiver_mobile")
    @Expose
    private String receiverMobile;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("duration_time")
    @Expose
    private String durationTime;
    @SerializedName("description_about")
    @Expose
    private String descriptionAbout;

    @SerializedName("isCompleted")
    @Expose
    private String isCompleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getSrId() {
        return srId;
    }

    public void setSrId(Integer srId) {
        this.srId = srId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getSrname() {
        return srname;
    }

    public void setSrname(String srname) {
        this.srname = srname;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getDescriptionAbout() {
        return descriptionAbout;
    }

    public void setDescriptionAbout(String descriptionAbout) {
        this.descriptionAbout = descriptionAbout;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

}