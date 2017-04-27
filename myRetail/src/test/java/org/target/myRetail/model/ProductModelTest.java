package org.target.myRetail.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * @author saramanzoor
 *
 */
public class ProductModelTest {

	//To test 
	@Test
	public void testProductPriceDetails() {
			Product product = new Product();
			ProductPrice productPrice = new ProductPrice();
			product.setProductID(15117729L);
			product.setProductName("The Big Lebowski (Blu-ray)");
			productPrice.setValue(2500.12f);
			productPrice.setCurrencyCode("USD");
			product.setProductPrice(productPrice);
			
			assertEquals(15117729, product.getProductID());
			assertEquals("The Big Lebowski (Blu-ray)", product.getProductName());
			assertEquals(2500.12f, product.getProductPrice().getValue(), 0.0f);
			assertEquals("USD", product.getProductPrice().getCurrencyCode() );
		}
}
