package com.streamsdemo.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.TimeZone;

public class DeviceDataEnriched
{
    @JsonProperty("device_id")
    private String deviceId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy hh:mm a",
            timezone="utc")
    private Date timestamp;
    private String data;
    @JsonProperty("device_type")
    private String deviceType;

    public DeviceDataEnriched()
    {
    }

    public DeviceDataEnriched(String deviceId, Date timestamp, String data, String deviceType)
    {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.data = data;
        this.deviceType =deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
