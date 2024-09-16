package com.leyunone.cloudcloud.bean;

public class CallBackParams {

    private String businessId;

    private String clientId;

    private Integer appId;
    
    private String userId;

    public CallBackParams() {

    }

    public CallBackParams(String businessId, String clientId, Integer appId, String userId) {
        this.businessId = businessId;
        this.clientId = clientId;
        this.appId = appId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CallBackParams{" +
                "businessId='" + businessId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", appId=" + appId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallBackParams that = (CallBackParams) o;

        if (businessId != null ? !businessId.equals(that.businessId) : that.businessId != null) {
            return false;
        }
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) {
            return false;
        }
        if (appId != null ? !appId.equals(that.appId) : that.appId != null) {
            return false;
        }
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = businessId != null ? businessId.hashCode() : 0;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (appId != null ? appId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    public String getBusinessId() {
        return businessId;
    }

    public CallBackParams setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public CallBackParams setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Integer getAppId() {
        return appId;
    }

    public CallBackParams setAppId(Integer appId) {
        this.appId = appId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CallBackParams setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}