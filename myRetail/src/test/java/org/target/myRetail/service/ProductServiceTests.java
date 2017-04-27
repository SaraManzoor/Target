package org.target.myRetail.service;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.target.myRetail.model.Product;
import org.target.myRetail.model.ProductPrice;

/**
 * @author saramanzoor
 *
 */
public class ProductServiceTests {

	// To check the GET call to the external API retrieves the product name.

	@Test
	public void testGetProductNameExternalAPI() throws JSONException {
		ProductService prodService = new ProductService();
		Response response = prodService.getProductName();
		Assert.assertEquals(200, response.getStatus());

		JSONObject jsonObject = new JSONObject(response.getEntity().toString());
		Assert.assertEquals("The Big Lebowski (Blu-ray)",
				jsonObject.getString("product_name"));
	}

	// To check GET call returns the product data as JSON and Accept PUT request at the same path /products/{id} to update product data
	@Test
	public void testGetAndPutProduct() throws JSONException {

		ProductService prodService = new ProductService();
		Product product = new Product();
		
		//generating productID's using random class
		Random ran = new Random();
		long pID = ran.nextInt(15117729) + 11111111;	
		ProductPrice productPrice = new ProductPrice();
		product.setProductID(pID);
		product.setProductName("The Big Lebowski (Blu-ray) (Widescreen");
		productPrice.setValue(1500);
		productPrice.setCurrencyCode("USD");
		product.setProductPrice(productPrice);
		JSONObject jsonProd = new JSONObject(product);
		Response response = prodService.getProduct(pID);
		assertEquals(404, response.getStatus());
		response = prodService.putProduct(pID, product);
		assertEquals(202, response.getStatus());
		response = prodService.getProduct(pID);
		assertEquals(200, response.getStatus());
		assertEquals(jsonProd.toString(), response.getEntity());
	}
}
