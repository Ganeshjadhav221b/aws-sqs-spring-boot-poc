package com.ns.awssqspoc.retryhandler;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SQSConfig {

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;
  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${cloud.aws.end-point.uri}")
  private String amazonEndpoint;


  @Bean
  public QueueMessagingTemplate queueMessagingTemplate() {
    return new QueueMessagingTemplate(amazonSQSAsync());
  }

  @Bean
  @Primary
  public AmazonSQSAsync amazonSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard().withEndpointConfiguration(endpointConfig())
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
        .build();
  }

  private EndpointConfiguration endpointConfig() {
    return new EndpointConfiguration(amazonEndpoint, region);
  }
}