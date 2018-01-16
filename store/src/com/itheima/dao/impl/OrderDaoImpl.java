package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;

public class OrderDaoImpl implements OrderDao {

	@Override
	/**
	 * 保存订单
	 */
	public void saveOrder(Orders order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getUser().getUid()};
		qr.update(C3P0Utils.getConnection(),sql,params);
	}

	@Override
	/**
	 * 保存订单项
	 */
	public void saceOrderItem(OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),
				orderItem.getSubtotal(),orderItem.getProduct().getPid(),
				orderItem.getOrder().getOid()};
		qr.update(C3P0Utils.getConnection(),sql,params);
		
	}

	@Override
	/**
	 * 根据uid查询订单总条数
	 */
	public int findTotalCount(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
	}

	@Override
	/**
	 * 分页查询我的订单
	 */
	public List<Orders> findOrderByPage(int startIndex, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		//查询到所有订单,缺少订单项集合
		List<Orders> orders = qr.query(sql, new BeanListHandler<>(Orders.class),uid,startIndex,pageSize);
		for (Orders order : orders) {
			sql = "select * from orderitem o,product p where o.oid=? and o.pid=p.pid";
			//包含订单项信息和商品信息
			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(),order.getOid());
			for (Map<String, Object> map : listMap) {
				//将订单项信息封装到实体中
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				//将商品信息封装到实体中
				Product product = new Product();
				BeanUtils.populate(product, map);
				//将商品实体封装到订单项中
				orderItem.setProduct(product);
				//将订单项添加到订单的订单项集合中
				List<OrderItem> listItem = order.getListItem();
				listItem.add(orderItem);
			}
		}
		return orders;
	}

}
