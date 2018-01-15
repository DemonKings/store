package com.itheima.domain;

public class OrderItem {
	//订单项id
	private String itemid;
	//数量
	private int count; 	//从购物项获得
	//小计
	private double subtotal;	//从购物项获得
	//商品信息
	private Product product;	//从购物项获得
	//所属订单
	private Orders order;	//从订单中获得
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	
}
