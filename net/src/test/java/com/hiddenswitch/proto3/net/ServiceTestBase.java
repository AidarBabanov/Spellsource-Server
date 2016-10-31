package com.hiddenswitch.proto3.net;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.hiddenswitch.proto3.net.amazon.Stack;
import com.hiddenswitch.proto3.net.amazon.StackConfiguration;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class ServiceTestBase<T extends Service> {
	public T service;
	private AmazonSQSClient queue;
	private DynamoDBMapper database;
	private AmazonDynamoDB dynamoDBEmbedded;
	private SQSRestServer elasticMQ;

	public abstract T getServiceInstance();


	@BeforeMethod
	public void setUp() throws Exception {
		elasticMQ = SQSRestServerBuilder.start();
		elasticMQ.waitUntilStarted();
		dynamoDBEmbedded = DynamoDBEmbedded.create().amazonDynamoDB();
		service = getServiceInstance();
		BasicAWSCredentials credentials = new BasicAWSCredentials("x", "y");
		service.setCredentials(credentials);
		database = new DynamoDBMapper(dynamoDBEmbedded);
		service.setDatabase(database);
		queue = new AmazonSQSClient(credentials);
		service.setQueue(queue);
		service.getQueue().setEndpoint("http://localhost:9324");
		StackConfiguration configuration = new StackConfiguration();
		configuration.credentials = credentials;
		configuration.dynamoDBClient = (AmazonDynamoDB) dynamoDBEmbedded;
		configuration.database = database;
		configuration.queue = queue;
		Stack.initializeStack(configuration);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		elasticMQ.stopAndWait();
		dynamoDBEmbedded.shutdown();
		queue.shutdown();
	}
}
