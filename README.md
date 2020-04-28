# localstack-sns-example
Example of bridging an SNS topic to an SQS queue using the [LocalStack](https://github.com/localstack/localstack) mock AWS environment.

## Building the Example
Run the following command to build the example application:

    ./gradlew clean build
    
## Running the Example
Follow the steps below to run the example application:

1. In the `localstack` directory, run the following commands to start localstack mock services:

        docker-compose up
    
2. Run the following command to start the example application:

        ./gradlew run
    
    If successful, you will see the following in the console:
    
        > Task :run
        [main] INFO example.localstack.sns.Application - Creating SNS topic: example-topic
        [main] INFO example.localstack.sns.Application - Created SNS topic: arn:aws:sns:us-east-1:000000000000:example-topic
        [main] INFO example.localstack.sns.Application - Creating SQS queue: example-queue
        [main] INFO example.localstack.sns.Application - Created SQS queue: arn:aws:sqs:us-east-1:000000000000:example-queue
        [main] INFO example.localstack.sns.Application - Subscribing queue to SNS topic: arn:aws:sqs:us-east-1:000000000000:example-queue
        [main] INFO example.localstack.sns.Application - Queue subscribed to SNS topic with subscription: arn:aws:sns:us-east-1:000000000000:example-topic:c8bbb3b5-f58e-4280-8404-3f0ea8af31d1
        [parallel-1] INFO example.localstack.sns.Application - Publishing: 1
        [parallel-3] INFO example.localstack.sns.Application - Publishing: 2
        [parallel-4] INFO example.localstack.sns.Application - Publishing: 3
        [parallel-5] INFO example.localstack.sns.Application - Publishing: 4
        [parallel-2] INFO example.localstack.sns.Application - Received: {MessageId: 7c9e801b-5c79-46bc-aaeb-17ac58996951,ReceiptHandle: 7c9e801b-5c79-46bc-aaeb-17ac58996951#97bd37f8-2f46-4ae8-9681-18f1a0c022e5,MD5OfBody: 8cf68eb6ef838a425024bc1fedf81863,Body: {"MessageId": "3c9780ce-e499-48f6-8847-efac07bee83f", "Type": "Notification", "Timestamp": "2020-04-28T18:20:09.115684Z", "Message": "{\"number\":5}", "TopicArn": "arn:aws:sns:us-east-1:000000000000:example-topic"},Attributes: {},MessageAttributes: {}}
        [parallel-2] INFO example.localstack.sns.Application - Received: {MessageId: 12fe6c89-76b9-4e75-b781-23e67bf64bad,ReceiptHandle: 12fe6c89-76b9-4e75-b781-23e67bf64bad#c5d0d636-b585-4310-b0c4-4f62c23ddd8b,MD5OfBody: d099345598af7b98fca1221c5c7bb23f,Body: {"MessageId": "3894250c-921e-48b1-87d8-e095a985e4df", "Type": "Notification", "Timestamp": "2020-04-28T18:20:10.198219Z", "Message": "{\"number\":6}", "TopicArn": "arn:aws:sns:us-east-1:000000000000:example-topic"},Attributes: {},MessageAttributes: {}}
        [parallel-2] INFO example.localstack.sns.Application - Received: {MessageId: d8ff3d58-44f7-4ca8-b5b4-d3b13971d2b0,ReceiptHandle: d8ff3d58-44f7-4ca8-b5b4-d3b13971d2b0#1dc0ad94-eee9-42a0-9cc9-0bd8a12e2c0e,MD5OfBody: 7521e892e6fdf10daf6c99c2ae321541,Body: {"MessageId": "0414ff74-bafc-44dc-be6c-9dc96ee14f2e", "Type": "Notification", "Timestamp": "2020-04-28T18:21:38.991605Z", "Message": "{\"number\":1}", "TopicArn": "arn:aws:sns:us-east-1:000000000000:example-topic"},Attributes: {},MessageAttributes: {}}
        [parallel-2] INFO example.localstack.sns.Application - Received: {MessageId: 0f53fac9-9c48-464f-88ac-f40f572f9d63,ReceiptHandle: 0f53fac9-9c48-464f-88ac-f40f572f9d63#7dc03d96-9eed-4d7b-bbb0-566999f26774,MD5OfBody: 29433c797ef5c7d63fcaa041545c2422,Body: {"MessageId": "6f54a53e-43c5-458c-aec8-ef2672dbcc0d", "Type": "Notification", "Timestamp": "2020-04-28T18:21:40.055993Z", "Message": "{\"number\":2}", "TopicArn": "arn:aws:sns:us-east-1:000000000000:example-topic"},Attributes: {},MessageAttributes: {}}
        [parallel-2] INFO example.localstack.sns.Application - Received: {MessageId: 5e882d62-c4cd-4646-bc36-ea3f7183b2c5,ReceiptHandle: 5e882d62-c4cd-4646-bc36-ea3f7183b2c5#72298654-904c-420c-9a4a-d9321a7b7a35,MD5OfBody: 5d977d1ffbfde8b5d8d80e42e252c93a,Body: {"MessageId": "42b2ae69-39e9-4b8b-b6ef-2241c1893256", "Type": "Notification", "Timestamp": "2020-04-28T18:21:41.144259Z", "Message": "{\"number\":3}", "TopicArn": "arn:aws:sns:us-east-1:000000000000:example-topic"},Attributes: {},MessageAttributes: {}}
        [parallel-6] INFO example.localstack.sns.Application - Publishing: 5

## License        
MIT License

Copyright (c) 2020 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.