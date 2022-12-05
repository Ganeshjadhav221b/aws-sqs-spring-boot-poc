package com.ns.awssqspoc.errorhandler;

import com.ns.awssqspoc.retryhandler.SqsMessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomErrorHandler {

  @Autowired
  SqsMessageProducerService sqsMessageProducerService;

  @Bean
  public KafkaListenerErrorHandler sqsErrorHandler() {
    return (message, e) -> {
      log.info("handle exception : {} : {}", message, e);
      sqsMessageProducerService.sendMessage(message.toString());
      return "Handled";
    };
  }
}
