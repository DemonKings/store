package com.itheima.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *	购物车 
 *
 */

public class Cart {
	//多个购物项
	private Map<String, CartItem> map = new HashMap<String, CartItem>();
	//总计
	private double total;
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	//将购物项加入购物车
	public void addCartItemToCart(CartItem ci){
		//获取购物项中商品的id
		String pid = ci.getProduct().getPid();
		//判断在购物车中是否存在该购物项
		if(map.containsKey(pid)){
			//存在
			//通过pid找到购物车中的购物项
			CartItem cartItem = map.get(pid);
			//将购物车中的该购物项数量加上要添加的购物项数量
			cartItem.setCount(cartItem.getCount()+ci.getCount());
		}else{
			//不存在
			map.put(pid, ci);
		}
		//计算总金额
		total += ci.getSubtotal();
	}
	//将购物项删除
	public void removeCartItemFromCart(String pid){
		CartItem cartItem = map.remove(pid);
		//设置总金额
		total -= cartItem.getSubtotal();
	}
	//将购物车清空
	public void clearCart(){
		map.clear();
		//设置总金额
		total = 0.0;
	}
	//为了方便遍历,将map转为list的方法
	public Collection<CartItem> getCartItemList(){
		Collection<CartItem> list = map.values();
		return list;
	}
}
