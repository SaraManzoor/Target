package org.target.myRetail.database;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

/**
 * @author saramanzoor
 *
 */
public class DatastoreTest {
	
	//To test the datastore connection and to check the table 'products' exists
	@Test
	public void testDatastoreGetTables() throws InterruptedException {
		AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient();
		amazonDynamoDBClient.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));
		amazonDynamoDBClient.setEndpoint("http://localhost:8000");
		
		ListTablesResult listTableResult = amazonDynamoDBClient.listTables();
		List<String> tableNames = listTableResult.getTableNames();
		
		if(tableNames.contains("products")) {
			DeleteTableRequest deleteTableRequest = new DeleteTableRequest("products"); 
			amazonDynamoDBClient.deleteTable(deleteTableRequest);
		}
		
		Table table = new Datastore().getTable();
		assertEquals("products", table.getTableName());
	}
}