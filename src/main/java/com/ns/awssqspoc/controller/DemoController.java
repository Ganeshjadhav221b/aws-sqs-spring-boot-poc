package com.ns.awssqspoc.controller;


import com.ns.awssqspoc.kafka.KafkaSenderExample;
import com.ns.awssqspoc.retryhandler.SqsMessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

  @Autowired(required = false)
  KafkaSenderExample kafkaSender;

  @Value("${kafka-config.topic-name}")
  String topicName;

  @GetMapping("/demo")
  public String echo() {
    log.info("Inside demo service1..");
    return "demo";
  }

  @PostMapping("/demo-1")
  public String add(@RequestParam("value") String value) {
    log.info("Inside demo service1..");
    kafkaSender.sendMessage("demo-1", value);
    return value + " pushed";
  }

  @PostMapping("/demo-2")
  public String add2(@RequestParam("value") String value) {
    log.info("Inside demo service1..");
    kafkaSender.sendMessage("demo-2", value);
    return value + " pushed";
  }


  @Autowired
  SqsMessageProducerService sqsMessageProducerService;
  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;
  @PostMapping("/sqs/demo-1")
  public String sendToQueue(@RequestParam("value") String value) {
    log.info("Inside demo service1..");
    Message<String> messageBuilt = MessageBuilder.withPayload(value).build();
//    queueMessagingTemplate.send(amazonSQSEndpoint, messageBuilt);
    sqsMessageProducerService.sendMessage(value);
    return value + " pushed";
  }
}
