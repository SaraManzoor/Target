package org.target.myRetail.database;

import org.target.myRetail.model.Product;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;


/** Method to retrieve product data for the given productID and
 *  Method to save product data
 * @author saramanzoor
 *
 */
public class ProductDatastoreAccess {

	private static Table table;
	
	public ProductDatastoreAccess() {
		table = new Datastore().getTable(); 		
	}
	
	/**
	 * Returns Item object with product ID
	 * @param productID
	 * @return item with the specified productID
	 */
	
	public Item getProduct(long productID) {		
		return table.getItem(new GetItemSpec().withPrimaryKey("product_id", productID));
	}
    
	/** Saves the item to the datastore
	 * @param product
	 * 
	 */
	public void putProduct(Product product) {
		Item item = new Item()
			.withPrimaryKey("product_id", product.getProductID())
			.withString("product_name", product.getProductName())
			.withString("currency", product.getProductPrice().getCurrencyCode())
			.withNumber("price", product.getProductPrice().getValue());
		table.putItem(item);
	}
	
}
