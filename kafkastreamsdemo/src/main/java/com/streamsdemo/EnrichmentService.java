package com.streamsdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamsdemo.kafkastreamsutils.DeserializationExceptionLoggerHandler;
import com.streamsdemo.entities.Config;
import com.streamsdemo.entities.DeviceData;
import com.streamsdemo.entities.DeviceDataEnriched;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

import org.apache.log4j.Logger;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class EnrichmentService
{
    private final static Logger LOGGER = Logger.getLogger(EnrichmentService.class);

    public static void main(String[] args)
    {
        Config serviceConfig = Config.loadConfig();

        LOGGER.info(String.format("loading with config {0}", serviceConfig));

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, serviceConfig.appName());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, serviceConfig.kafkaBroker());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,  new JsonSerde<>(DeviceData.class).getClass().getName());
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, DeserializationExceptionLoggerHandler.class);


        final Serializer<DeviceData> jsonSerializer = new JsonSerializer<>();
        final Deserializer<DeviceData> jsonDeserializer = new JsonDeserializer<>(DeviceData.class,
                 new ObjectMapper(), false);

        final Serde<DeviceData> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        StreamsBuilder builder = new StreamsBuilder();
        Consumed consumed = Consumed.with(jsonSerde, jsonSerde);

        KStream<String, DeviceData> textLines =  builder.stream(serviceConfig.inputTopic(), consumed);

        KStream<String, DeviceDataEnriched> stream= textLines.mapValues(new ValueMapperDeviceEnrich());

        Produced<String, DeviceDataEnriched> produced = Produced.with(new JsonSerde<>(String.class),
                new JsonSerde<>(DeviceDataEnriched.class));

        stream.to(serviceConfig.outputTopic(), produced);

        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        LOGGER.info("start consumming");

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}