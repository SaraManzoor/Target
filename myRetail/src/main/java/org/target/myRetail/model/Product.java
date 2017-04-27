package org.target.myRetail.model;
/**
 * Product bean class
 * @author saramanzoor
 *
 */

public class Product {

	private long productID;
	private String productName;
	private ProductPrice productPrice;

	public Product(long productID, String productName, ProductPrice productPrice) {
		this.productID = productID;
		this.productName = productName;
		this.productPrice = productPrice;
	}
    
	public Product() {
	}
	
	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

}
