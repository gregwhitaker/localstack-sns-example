package example.localstack.sns;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.localstack.sns.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Runs the SNS LocalStack example application.
 */
public class Application {
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static void main(String... args) throws Exception {
    final AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4575", "us-east-1"))
        .build();

    final AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4576", "us-east-1"))
        .build();

    LOG.info("Creating SNS topic: example-topic");
    final CreateTopicResult createTopicResult = amazonSNS.createTopic("example-topic");
    final String topicArn = createTopicResult.getTopicArn();
    LOG.info("Created SNS topic: {}", topicArn);

    LOG.info("Creating SQS queue: example-queue");
    final CreateQueueResult createQueueResult = amazonSQS.createQueue("example-queue");
    final String queueUrl = createQueueResult.getQueueUrl();
    final GetQueueAttributesResult queueAttributes = amazonSQS.getQueueAttributes(queueUrl, Arrays.asList("QueueArn"));
    final String queueArn = queueAttributes.getAttributes().get("QueueArn");
    LOG.info("Created SQS queue: {}", queueArn);

    LOG.info("Subscribing queue to SNS topic: {}", queueArn);
    SubscribeResult subscribeResult = amazonSNS.subscribe(topicArn, "sqs", queueArn);
    LOG.info("Queue subscribed to SNS topic with subscription: {}", subscribeResult.getSubscriptionArn());

    CountDownLatch latch = new CountDownLatch(1_000);

    // Publish messages onto SNS topic
    Flux.range(1, 1_000)
        .delayElements(Duration.ofSeconds(1))
        .doOnNext(i -> {
          try {
            LOG.info("Publishing: {}", i);
            amazonSNS.publish(topicArn, MAPPER.writeValueAsString(new Message(i)));
          } catch (JsonProcessingException e) {
            LOG.error("Error converting SNS message to JSON", e);
          }
        })
        .subscribe();

    // Subscribe to messages from the SQS queue
    Flux.interval(Duration.ofSeconds(5))
        .subscribeOn(Schedulers.boundedElastic())
        .doOnNext(t -> {
          ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
              .withQueueUrl(queueUrl)
              .withMaxNumberOfMessages(10);

          ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(receiveMessageRequest);
          receiveMessageResult.getMessages().forEach(message -> {
            LOG.info("Received: {}", message);
            amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
            latch.countDown();
          });
        })
        .subscribe();

    latch.await();
  }
}
