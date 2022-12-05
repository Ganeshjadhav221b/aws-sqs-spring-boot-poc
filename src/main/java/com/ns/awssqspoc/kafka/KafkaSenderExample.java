package com.ns.awssqspoc.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
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