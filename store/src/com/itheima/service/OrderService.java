package com.itheima.service;

import com.itheima.domain.Orders;
import com.itheima.utils.PageBean;

public interface OrderService {

	void commitOrder(Orders order) throws Exception;

	PageBean<Orders> findOrderByPage(int pageNumber, int pageSize, String uid)throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void updateOrder(Orders order)throws Exception;


}
