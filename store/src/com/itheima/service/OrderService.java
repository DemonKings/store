package com.itheima.service;

import com.itheima.domain.Orders;

public interface OrderService {

	void commitOrder(Orders order) throws Exception;


}
