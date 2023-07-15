package com.ns.awssqspoc.retryhandler;

import com.ns.awssqspoc.exceptions.ShouldSkipBothRetriesException;
import com.ns.awssqspoc.kafka.KafkaListenerExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

  @Autowired
  KafkaListenerExample kafkaListenerExample;
  @Autowired
  SqsMessageProducerService sqsMessageProducerService;

  @SqsListener(value = "${cloud.aws.end-point.sqs}")
  public void processMessage(GenericMessage<String> message) throws ShouldSkipBothRetriesException {
    log.info("Message from SQS {}", message);
//    kafkaListenerExample.listener(message.getPayload());
    sqsMessageProducerService.sendMessageWithRetries2(message.getPayload());
//    sqsMessageProducerService.sendMessageWithRetries(message.getPayload()); //infinite retries
  }
}
/**
 * ""SentTimestamp" -> "1670230544809" "ReceiptHandle" ->
 * "dd789c41-9a73-4fb7-af1c-4388cdfb5ae3#c4338c65-9c9d-476f-8c4d-5471ed832e89" "kafka_timestampType"
 * -> "CREATE_TIME" "MessageGroupId" -> "" "SenderId" -> "127.0.0.1" "kafka_receivedTopic" ->
 * "demo-1" "LogicalResourceId" -> "http://localhost:9324/queue/default" "kafka_offset" ->
 * {Long@9003} 22 "ApproximateReceiveCount" -> "2" "Visibility" -> {QueueMessageVisibility@9007}
 * "kafka_receivedPartitionId" -> {Integer@9009} 0 "MessageDeduplicationId" -> ""
 * "kafka_receivedTimestamp" -> {Long@9013} 1670230544489 "lookupDestination" ->
 * "http://localhost:9324/queue/default" "ApproximateFirstReceiveTimestamp" -> "1670230549828"
 * "kafka_groupId" -> "my-group" "MessageId" -> "dd789c41-9a73-4fb7-af1c-4388cdfb5ae3"
 * <p>
 * <p>
 * Message from SQS GenericMessage [payload=7800100, headers={SentTimestamp=1670230681934,
 * ReceiptHandle=f9809146-11f4-41f0-9bca-30b70dfbb6ed#e83c6b90-0346-4852-8161-bbad00d48ed9,
 * kafka_timestampType=CREATE_TIME, MessageGroupId=, SenderId=127.0.0.1, kafka_receivedTopic=demo-1,
 * LogicalResourceId=http://localhost:9324/queue/default, kafka_offset=24,
 * ApproximateReceiveCount=3,
 * Visibility=org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility@687344cb,
 * kafka_receivedPartitionId=0, MessageDeduplicationId=, kafka_receivedTimestamp=1670230681752,
 * lookupDestination=http://localhost:9324/queue/default,
 * ApproximateFirstReceiveTimestamp=1670230686960, kafka_groupId=my-group,
 * MessageId=f9809146-11f4-41f0-9bca-30b70dfbb6ed}]
 * <p>
 * <p>
 * Message from SQS GenericMessage [payload=7800100, headers={SentTimestamp=1670230681934,
 * ReceiptHandle=f9809146-11f4-41f0-9bca-30b70dfbb6ed#e83c6b90-0346-4852-8161-bbad00d48ed9,
 * kafka_timestampType=CREATE_TIME, MessageGroupId=, SenderId=127.0.0.1, kafka_receivedTopic=demo-1,
 * LogicalResourceId=http://localhost:9324/queue/default, kafka_offset=24,
 * ApproximateReceiveCount=3,
 * Visibility=org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility@687344cb,
 * kafka_receivedPartitionId=0, MessageDeduplicationId=, kafka_receivedTimestamp=1670230681752,
 * lookupDestination=http://localhost:9324/queue/default,
 * ApproximateFirstReceiveTimestamp=1670230686960, kafka_groupId=my-group,
 * MessageId=f9809146-11f4-41f0-9bca-30b70dfbb6ed}]
 * <p>
 * <p>
 * Message from SQS GenericMessage [payload=7800100, headers={SentTimestamp=1670230681934,
 * ReceiptHandle=f9809146-11f4-41f0-9bca-30b70dfbb6ed#e83c6b90-0346-4852-8161-bbad00d48ed9,
 * kafka_timestampType=CREATE_TIME, MessageGroupId=, SenderId=127.0.0.1, kafka_receivedTopic=demo-1,
 * LogicalResourceId=http://localhost:9324/queue/default, kafka_offset=24,
 * ApproximateReceiveCount=3,
 * Visibility=org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility@687344cb,
 * kafka_receivedPartitionId=0, MessageDeduplicationId=, kafka_receivedTimestamp=1670230681752,
 * lookupDestination=http://localhost:9324/queue/default,
 * ApproximateFirstReceiveTimestamp=1670230686960, kafka_groupId=my-group,
 * MessageId=f9809146-11f4-41f0-9bca-30b70dfbb6ed}]
 */

/**
 *
 * Message from SQS GenericMessage [payload=7800100, headers={SentTimestamp=1670230681934,
 * ReceiptHandle=f9809146-11f4-41f0-9bca-30b70dfbb6ed#e83c6b90-0346-4852-8161-bbad00d48ed9,
 * kafka_timestampType=CREATE_TIME, MessageGroupId=, SenderId=127.0.0.1, kafka_receivedTopic=demo-1,
 * LogicalResourceId=http://localhost:9324/queue/default, kafka_offset=24, ApproximateReceiveCount=3,
 * Visibility=org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility@687344cb,
 * kafka_receivedPartitionId=0, MessageDeduplicationId=, kafka_receivedTimestamp=1670230681752,
 * lookupDestination=http://localhost:9324/queue/default, ApproximateFirstReceiveTimestamp=1670230686960,
 * kafka_groupId=my-group, MessageId=f9809146-11f4-41f0-9bca-30b70dfbb6ed}]
 *
 */