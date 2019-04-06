package com.streamsdemo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceData
{
    @JsonProperty("device_id")
    private String deviceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy hh:mm a", timezone = "utc")
    public Date timestamp;

    private String data;

    public DeviceData()
    {
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
}
