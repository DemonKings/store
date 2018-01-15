package com.itheima.domain;
/**
 * 购物项
 *
 */
public class CartItem {
	//商品信息
	private Product product;
	//购买数量
	private int count;
	//小计
	private double subtotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//小计
	public double getSubtotal() {
		return product.getShop_price()*count;
	}
	
}
