package com.ns.awssqspoc.retryhandler;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.ns.awssqspoc.Pojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsMessageProducerService {


  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;
  @Value("${cloud.aws.end-point.sqs}")
  private String amazonSQSEndpoint;

  public void sendMessage(String value) {
    Message<String> message = MessageBuilder.withPayload(value).build();

    Pojo hello123 = new Pojo(value);
    Message<Pojo> pojoMessage = new Message<Pojo>() {
      @Override
      public Pojo getPayload() {
        return hello123;
      }

      @Override
      public MessageHeaders getHeaders() {
        return message.getHeaders();
      }
    };
    queueMessagingTemplate.send(amazonSQSEndpoint, pojoMessage);
    log.info("message sent: {}", message);
  }

  public void sendMessageWithRetries2(String message) {
    Pojo hello123 = new Pojo(message);
    if (message.contains("1")) {
      throw new RuntimeException();
    }
  }
  public void sendMessageWithRetries(String message) {
    Pojo hello123 = new Pojo(message);
    try {
      if (message.contains("1")) {
        throw new RuntimeException();
      }
    }catch (Exception e){

      Message<String> messageBuilt = MessageBuilder.withPayload(message).build();

      Message<Pojo> pojoMessage = new Message<Pojo>() {
        @Override
        public Pojo getPayload() {
          return hello123;
        }

        @Override
        public MessageHeaders getHeaders() {
          return messageBuilt.getHeaders();
        }
      };
          queueMessagingTemplate.send(amazonSQSEndpoint, pojoMessage);
      log.info("message sent: {}", message);
    }

  }
}