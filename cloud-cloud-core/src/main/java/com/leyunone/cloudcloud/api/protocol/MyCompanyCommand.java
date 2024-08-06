package com.leyunone.cloudcloud.api.protocol;

import java.io.Serializable;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/13 10:53
 */
public class MyCompanyCommand implements Serializable {

    private Long deviceId;

    private Object value;

    private String signCode;

    private Integer functionId;

    public Long getDeviceId() {
        return deviceId;
    }

    public MyCompanyCommand setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public MyCompanyCommand setValue(Object value) {
        this.value = value;
        return this;
    }

    public String getSignCode() {
        return signCode;
    }

    public MyCompanyCommand setSignCode(String signCode) {
        this.signCode = signCode;
        return this;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public MyCompanyCommand setFunctionId(Integer functionId) {
        this.functionId = functionId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyCompanyCommand that = (MyCompanyCommand) o;

        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }
        if (signCode != null ? !signCode.equals(that.signCode) : that.signCode != null) {
            return false;
        }
        return functionId != null ? functionId.equals(that.functionId) : that.functionId == null;
    }

    @Override
    public int hashCode() {
        int result = deviceId != null ? deviceId.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (signCode != null ? signCode.hashCode() : 0);
        result = 31 * result + (functionId != null ? functionId.hashCode() : 0);
        return result;
    }
}
