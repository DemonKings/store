package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;

public class ProductDaoImpl implements ProductDao {

	@Override
	/**
	 * 查询热门商品
	 */
	public List<Product> findHotPro() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where is_hot=? and pflag=? limit ?";
		Object[] params = {Constant.PRODUCT_IS_HOT,Constant.PRODUCT_NOT_DOWN,Constant.NUM_OF_HOT};
		return qr.query(sql, new BeanListHandler<>(Product.class),params);
	}

	@Override
	/**
	 * 查询最新商品
	 */
	public List<Product> findNewPro() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where pflag=? order by pdate desc limit ?";
		Object[] params = {Constant.PRODUCT_NOT_DOWN,Constant.NUM_OF_HOT};
		return qr.query(sql, new BeanListHandler<>(Product.class),params);
	}

}
