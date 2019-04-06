package com.streamsdemo.entities;

import com.streamsdemo.EnrichmentService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
    private static final String APP_NAME = "app_name";
    private static final String KAFKA_BROKER = "kafka_broker";
    private static final String INPUT_TOPIC = "input_topic";
    private static final String OUTPUT_TOPIC = "output_topic";
    private final Properties properties;


    public Config(Properties properties)
    {
        this.properties = properties;
    }

    public String appName()
    {
        return properties.getProperty(APP_NAME);
    }

    public String kafkaBroker()
    {
        return properties.getProperty(KAFKA_BROKER);
    }

    public String inputTopic()
    {
        return properties.getProperty(INPUT_TOPIC);
    }

    public String outputTopic()
    {
        return properties.getProperty(OUTPUT_TOPIC);
    }

    public  static Config loadConfig()
    {
        InputStream inputStream = null;
        try
        {
            Properties properties = new Properties();
            inputStream = EnrichmentService.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);

            return new Config(properties);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if(inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public String toString() {
        return "Config{" +
                "properties=" + properties +
                '}';
    }
}
