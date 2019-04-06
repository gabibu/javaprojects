package com.streamsdemo;

import com.streamsdemo.entities.DeviceData;
import com.streamsdemo.entities.DeviceDataEnriched;
import com.streamsdemo.entities.Text;
import org.apache.kafka.streams.kstream.ValueMapper;

public class ValueMapperDeviceEnrich implements ValueMapper<DeviceData, DeviceDataEnriched>
{
    @Override
    public DeviceDataEnriched apply(DeviceData deviceData)
    {
        String deviceType = (deviceData.getData() != null && deviceData.getData().contains(Text.IPHONE)) ?   Text.SMART_PHONE
                : Text.OTHER;

        return new DeviceDataEnriched(deviceData.getDeviceId(),
                deviceData.getTimestamp(), deviceData.getData(),  deviceType);
    }
}
