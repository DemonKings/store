package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

public interface OrderDao {

	void saveOrder(Orders order) throws SQLException;

	void saceOrderItem(OrderItem orderItem) throws SQLException;

	int findTotalCount(String uid) throws SQLException;

	List<Orders> findOrderByPage(int startIndex, int pageSize, String uid) throws Exception;

}
