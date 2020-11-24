
package com.akar.aakar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpApi {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("details")
    @Expose
    private List<Datum> details = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Datum> getDetails() {
        return details;
    }

    public void setDetails(List<Datum> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}