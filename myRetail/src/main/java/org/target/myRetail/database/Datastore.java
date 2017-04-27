package org.target.myRetail.database;

import java.util.ArrayList;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;


/** Setting up the client for accessing the DynamoDB, and creating the table 'products'
 * @author saramanzoor
 *
 */
public class Datastore {
	
	private static AmazonDynamoDBClient amazonDynamoDBClient;
	private static String tableName = "products";
		
	/** Create table if it does not exist
	 * 
	 * @return instance to the table product
	 */
	public Table getTable() {
		initializeClient();		
		if(doesTableExist(tableName)) {
			return new Table(amazonDynamoDBClient, tableName);
		} else {
			
			//create table
			CreateTableRequest request = new CreateTableRequest();
			
			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			//defining the primary key of the product table
			keySchema.add(new KeySchemaElement().withAttributeName("product_id").withKeyType(KeyType.HASH));
			
			// defining the datatype of the primary key
			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("product_id").withAttributeType("N"));
			
			request.setTableName(tableName);
			request.setAttributeDefinitions(attributeDefinitions);
			request.setKeySchema(keySchema);
			
			//expected number of read/writes to the table per second
			request.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(5L)
	        .withWriteCapacityUnits(5L));
						
			//check if the table is created or not
			if(amazonDynamoDBClient.createTable(request).getTableDescription().getTableStatus().equals(TableStatus.ACTIVE.name())) {
				return new Table(amazonDynamoDBClient, tableName);
			} else {
				return null;
			}
		}		
	}
	
	
	/** method to initialize the DynamoDB client
	 * sets the regional endpoint for this client's service calls  and configure the endpoint 
	 *
	 */
	private void initializeClient() {		
		if(amazonDynamoDBClient == null) {
			amazonDynamoDBClient = new AmazonDynamoDBClient();
			amazonDynamoDBClient.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));
			amazonDynamoDBClient.setEndpoint("http://localhost:8000");
		}	
	}
	

	/**
	 * 
	 * @param tableName
	 * @return table status if the table already exists
	 */
	private boolean doesTableExist(String tableName) {
	    try {
	        TableDescription table = amazonDynamoDBClient.describeTable(new DescribeTableRequest(tableName))
	                .getTable();
	        return TableStatus.ACTIVE.toString().equals(table.getTableStatus());
	    } catch (ResourceNotFoundException rnfe) {
	        return false;
	    }
	}
	
}
