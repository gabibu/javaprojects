package com.streamsdemo.kafkastreamsutils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.log4j.Logger;

import java.util.Map;


public class DeserializationExceptionLoggerHandler implements org.apache.kafka.streams.errors.DeserializationExceptionHandler  //implements ProductionExceptionHandler
{
    private final static Logger LOGGER = Logger.getLogger(DeserializationExceptionLoggerHandler.class);

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext processorContext, ConsumerRecord<byte[], byte[]> consumerRecord, Exception e) {
        LOGGER.error(String.format("error from record {0}", consumerRecord), e);
        return DeserializationHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
