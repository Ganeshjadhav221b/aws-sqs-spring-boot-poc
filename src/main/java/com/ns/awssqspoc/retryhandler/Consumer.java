package com.ns.awssqspoc.retryhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

  @SqsListener(value = "${cloud.aws.end-point.sqs}", deletionPolicy =
      SqsMessageDeletionPolicy.ON_SUCCESS)
  public void processMessage(GenericMessage<String> message) {
    log.info("Message from SQS {}", message);
  }
}
