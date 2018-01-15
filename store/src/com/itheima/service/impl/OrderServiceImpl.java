package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void commitOrder(Orders order) throws Exception {
		try {
			//开启手动事务
			C3P0Utils.startTransaction();
			//调用dao将保存订单
			//OrderDao dao = new OrderDaoImpl();
			OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");
			dao.saveOrder(order);
			//获取所有订单项
			List<OrderItem> listItem = order.getListItem();
			//遍历所有订单项
			for (OrderItem orderItem : listItem) {
				//调用dao保存订单项
				dao.saceOrderItem(orderItem);
			}
			//提交事务
			C3P0Utils.commitAndClose();
		} catch (SQLException e) {
			e.printStackTrace();
			//回滚事务
			C3P0Utils.rollbackAndClose();
			throw e;
		}
	}


}
