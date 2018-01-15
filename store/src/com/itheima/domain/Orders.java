package com.itheima.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orders {
	// 订单id
	private String oid;	// 后台生成
	// 订单生成时间
	private Date ordertime;	// 创建当前时间
	// 订单总金额
	private double total;	// 从购物车中获取
	// 订单状态
	private int state;		// 在创建订单时设置    1:未支付     2:未发货	3:已发货	4:订单已完成
	// 收货人
	private String name;    // 订单支付时,用户填写
	// 收货地址
	private String address; // 订单支付时,用户填写
	// 收货人联系方式
	private String telephone; // 订单支付时,用户填写
	// 所数用户
	private User user;		// 
	// 当前订单所包含的订单项
	private List<OrderItem> listItem = new ArrayList<OrderItem>();
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getListItem() {
		return listItem;
	}
	public void setListItem(List<OrderItem> listItem) {
		this.listItem = listItem;
	}
	
}
