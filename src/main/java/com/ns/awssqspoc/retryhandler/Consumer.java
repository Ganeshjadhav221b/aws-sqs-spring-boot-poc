package com.ns.awssqspoc.retryhandler;

import com.ns.awssqspoc.exceptions.FirstException;
import com.ns.awssqspoc.exceptions.ShouldSkipBothRetriesException;
import com.ns.awssqspoc.kafka.KafkaListenerExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

  @Autowired
  KafkaListenerExample kafkaListenerExample;

  @SqsListener(value = "${cloud.aws.end-point.sqs}")
  public void processMessage(GenericMessage<String> message) throws ShouldSkipBothRetriesException {
    log.info("Message from SQS {}", message);
    kafkaListenerExample.listener(message.getPayload());
  }
}
/**
 * "LogicalResourceId" -> "http://localhost:9324/queue/default"
 * "ApproximateReceiveCount" -> "1"
 * "SentTimestamp" -> "1670223964491"
 * "ReceiptHandle" -> "14164f0f-c54d-41ca-9f0a-0986f256ba57#93d81e6a-f5b9-460e-ba55-a0e023d1c2a0"
 * "Visibility" -> {QueueMessageVisibility@9001}
 * "MessageGroupId" -> ""
 * "SenderId" -> "127.0.0.1"
 * "MessageDeduplicationId" -> ""
 * "lookupDestination" -> "http://localhost:9324/queue/default"
 * "ApproximateFirstReceiveTimestamp" -> "1670223969509"
 * "MessageId" -> "14164f0f-c54d-41ca-9f0a-0986f256ba57"
 */