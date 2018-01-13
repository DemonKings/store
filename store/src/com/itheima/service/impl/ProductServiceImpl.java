package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	/**
	 * 查询热门商品
	 */
	public List<Product> findHotPro() throws Exception {
		//调用dao查询热门商品
		ProductDao dao = new ProductDaoImpl();
		return dao.findHotPro();
	}

	@Override
	/**
	 * 查询最新商品
	 */
	public List<Product> findNewPro() throws Exception {
		//调用dao查询最新商品
		ProductDao dao = new ProductDaoImpl();
		return dao.findNewPro();
	}

	@Override
	/**
	 * 根据pid查询商品
	 */
	public Product findByPid(String pid) throws Exception {
		//调用dao查询商品
		ProductDao dao = new ProductDaoImpl();
		return dao.findByPid(pid);
	}

}
