package org.target.myRetail.database;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.target.myRetail.database.ProductDatastoreAccess;
import org.target.myRetail.model.Product;
import org.target.myRetail.model.ProductPrice;

import com.amazonaws.services.dynamodbv2.document.Item;

/**
 * @author saramanzoor
 *
 */
public class ProductDatastoreAccessTest {

	// To check product is retrieved successfully and saved to/from the datastore
	@Test
	public void testGetAndPutProduct() {
		ProductDatastoreAccess productDatastoreAccess = new ProductDatastoreAccess();

		Item item = productDatastoreAccess.getProduct(1L);
		assertNull(item);

		Product product = new Product();
		ProductPrice productPrice = new ProductPrice();
		product.setProductID(15117729L);
		product.setProductName("The Big Lebowski (Blu-ray)");
		productPrice.setValue(2500.12f);
		productPrice.setCurrencyCode("USD");
		product.setProductPrice(productPrice);

		productDatastoreAccess.putProduct(product);

		item = productDatastoreAccess.getProduct(15117729L);

		assertEquals(2500.12f, item.getFloat("price"), 0.0f);
		assertEquals("USD", item.getString("currency"));
		assertEquals(15117729L, item.getLong("product_id"));
		assertEquals("The Big Lebowski (Blu-ray)",
				item.getString("product_name"));
		
		Product product1 = new Product();
		ProductPrice productPrice1 = new ProductPrice();
		product1.setProductID(15117729L);
		product1.setProductName("The Big Lebowski (Blu-ray)");
		productPrice1.setValue(2500.13f);
		productPrice1.setCurrencyCode("INR");
		product1.setProductPrice(productPrice1);

		productDatastoreAccess.putProduct(product1);
		item = productDatastoreAccess.getProduct(15117729L);

		assertEquals(2500.13f, item.getFloat("price"), 0.0f);
		assertEquals("INR", item.getString("currency"));
		assertEquals(15117729L, item.getLong("product_id"));
		Assert.assertEquals("The Big Lebowski (Blu-ray)",
				item.getString("product_name"));	
	}
}
