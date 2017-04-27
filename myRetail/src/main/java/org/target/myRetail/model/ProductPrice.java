package org.target.myRetail.model;

/**
 * Product Price bean class
 * @author saramanzoor
 *
 */
public class ProductPrice {
	private float value;
	private String currencyCode;
	
	public ProductPrice(float value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}
	
	public ProductPrice() {
	}

	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
}
