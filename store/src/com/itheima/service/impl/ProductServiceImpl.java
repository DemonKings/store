package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.PageBean;

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

	@Override
	/**
	 * 分页查询导航栏分类下的商品信息
	 */
	public PageBean<Product> findByPage(String cid, int pageNumber, int pageSize) throws Exception {
		//调用dao查询当前分类下商品总条数
		ProductDao dao = new ProductDaoImpl();
		int totalCount = dao.findTotalCount(cid);
		//创建PageBean对象
		PageBean<Product> pageBean = new PageBean<>(pageNumber, pageSize, totalCount);
		//获取起始索引
		int startIndex = pageBean.getStartIndex();
		//调用dao分页查询商品信息
		List<Product> data = dao.findByCid(cid,startIndex,pageSize);
		//将数据封装到pagebean
		pageBean.setData(data);
		return pageBean;
	}

}
