package org.target.myRetail.service;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;
import org.target.myRetail.database.ProductDatastoreAccess;
import org.target.myRetail.model.Product;
import org.target.myRetail.model.ProductPrice;

import com.amazonaws.services.dynamodbv2.document.Item;


/**
 *  Class to respond to GET request at products/{id}, GET request to a external API to get product name
 *   and accept a PUT request to create/update product data for the given productID
 * 
 * @author saramanzoor
 *
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {

	private ProductDatastoreAccess productDatastoreAccess;

	public ProductService() {
		productDatastoreAccess = new ProductDatastoreAccess();
	}

	@GET
	@Path("/redsky")
	public Response getProductName() {
		
		//read JSON data from an external file for retrieving productName
		 try {
			 BufferedReader reader = new BufferedReader(new FileReader("/Users/saramanzoor/Documents/workspace/myRetail/src/main/resources/ProductExternal.txt"));
			 StringBuilder stringBuilder = new StringBuilder();
			 String line = null;
			 while((line = reader.readLine()) != null) {
				 stringBuilder.append(line);			 
			 }
			 reader.close();
			 JSONObject json = new JSONObject(stringBuilder.toString());
			 String productName = json.getJSONObject("product").getJSONObject("item").getJSONObject("product_description").getString("title");
			 return Response.ok(new JSONObject().put("product_name", productName).toString()).build();
		 } catch (Exception e) {
			 return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		 }	
	}

	@GET
	@Path("/{product_id}")
	public Response getProduct(@PathParam("product_id") long productId) {
		
		// Get the product from the datastore
		Item result = productDatastoreAccess.getProduct(productId);
		
		// If the productID does not exist then throw back a 404 error response code with a message string.
		try {
			if (result == null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("error",
						"The requesting product entity does not exist");
				return Response.status(Status.NOT_FOUND)
						.entity(jsonObject.toString()).build();
			} else {
				JSONObject jsonObjectInterim = new JSONObject();
				jsonObjectInterim.put("value", result.getFloat("price"));
				jsonObjectInterim.put("currencyCode", result.getString("currency"));
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("productID", result.getLong("product_id"));
				jsonObject.put("productName", result.getString("product_name"));
				jsonObject.put("productPrice", jsonObjectInterim);
				return Response.ok(jsonObject.toString()).build();
			}
		} catch (JSONException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{product_id}")
	public Response putProduct(@PathParam("product_id") long productId, Product product) {
		try {
			JSONObject jsonProd = new JSONObject(product);
			Product prod = new Product(jsonProd.getLong("productID"),
					jsonProd.getString("productName"), new ProductPrice(
							(float) jsonProd.getJSONObject("productPrice")
									.getDouble("value"), jsonProd
									.getJSONObject("productPrice").getString(
											"currencyCode")));
			//put product data to the products table
			productDatastoreAccess.putProduct(prod);
			return Response.status(Status.ACCEPTED).build();
		} catch (JSONException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
