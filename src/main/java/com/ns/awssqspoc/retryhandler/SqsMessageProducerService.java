package com.ns.awssqspoc.retryhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsMessageProducerService {


  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;
  @Value("${cloud.aws.end-point.sqs}")
  private String amazonSQSEndpoint;

  public void sendMessage(Message<?> message) {
    queueMessagingTemplate.send(amazonSQSEndpoint, message);
    log.info("message sent: {}", message);
  }
}