package com.ns.awssqspoc.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "kafka-config.avro-enabled", havingValue = "false", matchIfMissing = true)
public class KafkaSenderExample {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  KafkaSenderExample(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(String topicName, String message) {
    kafkaTemplate.send(topicName, message);
  }
}