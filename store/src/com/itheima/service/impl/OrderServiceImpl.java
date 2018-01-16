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
import com.itheima.utils.PageBean;

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

	@Override
	/**
	 * 分页查询我的订单
	 */
	public PageBean<Orders> findOrderByPage(int pageNumber, int pageSize, String uid) throws Exception {
		//调用dao查询总条数
		OrderDao dao = (OrderDao)BeanFactory.getBean("OrderDao");
		int totalCount = dao.findTotalCount(uid);
		//创建pagebean对象
		PageBean<Orders> pb = new PageBean<>(pageNumber, pageSize, totalCount);
		//获取起始索引
		int startIndex = pb.getStartIndex();
		//调用dao分页查询所有订单
		List<Orders> data = dao.findOrderByPage(startIndex,pageSize,uid);
		pb.setData(data);
		return pb;
	}

	@Override
	/**
	 * 根据订单编号查询订单
	 */
	public Orders findOrderByOid(String oid) throws Exception {
		//调用dao查询
		OrderDao dao = (OrderDao)BeanFactory.getBean("OrderDao");
		return dao.findOrderByOid(oid);
	}

	@Override
	/**
	 * 更新订单数据
	 */
	public void updateOrder(Orders order) throws Exception {
		//调用dao更新订单
		OrderDao dao = (OrderDao)BeanFactory.getBean("OrderDao");
		dao.updateOrder(order);
	}


}
