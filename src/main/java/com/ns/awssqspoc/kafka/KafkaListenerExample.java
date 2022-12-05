package com.ns.awssqspoc.kafka;

import com.ns.awssqspoc.annotations.CustomAnnotation;
import com.ns.awssqspoc.exceptions.CustomRetryableException;
import com.ns.awssqspoc.exceptions.FirstException;
import com.ns.awssqspoc.exceptions.SecondException;
import com.ns.awssqspoc.exceptions.ShouldSkipBothRetriesException;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "kafka-config.avro-enabled", havingValue = "false", matchIfMissing = true)
class KafkaListenerExample {

  //Created 3 topics at app. start-
  //Topic: demo-1-retry-3000 -- messages will continue to stay
  //Topic: demo-1-retry-6000
  //Topic: demo-1-dlt
//  @RetryableTopic(include = {CustomRetryableException.class},
//      attempts = "3",
//      backoff = @Backoff(delay = 4000, multiplier = 2, maxDelay = 10000),
//  listenerContainerFactory = "kafkaRetryListenerContainerFactory",
//  kafkaTemplate = "kafkaRetryTemplate",
//  autoCreateTopics = "true")
  @KafkaListener(topics = "demo-1", groupId = "my-group", containerFactory =
      "kafkaListenerContainerFactory"
  )
  @CustomAnnotation(topicName = "demo-1")
  void listener(String value) throws ShouldSkipBothRetriesException {

    log.info(ZonedDateTime.now() + " ko mila hai ye - " + value);
    if (value.endsWith("0")) {
      throw new CustomRetryableException();
    }
    //Without configs - 10 times --all right away
    //With @RetryableTopic -
    if (value.endsWith("1")) {
      throw new FirstException();
    }
    if (value.endsWith("2")) {
      throw new SecondException();
    }
    if (value.endsWith("9")) {
      throw new ShouldSkipBothRetriesException();
    }
  }

}