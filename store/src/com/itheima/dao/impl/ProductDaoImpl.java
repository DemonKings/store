package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.itheima.dao.Category;
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

	@Override
	/**
	 * 根据pid查询商品
	 */
	/*public Product findByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where pid=?";
		return qr.query(sql, new BeanHandler<>(Product.class),pid);
	}*/
	public Product findByPid(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "SELECT * FROM product pro,category c WHERE pro.cid=? AND pro.cid = c.cid";
		List<Map<String, Object>> list = qr.query(sql, new MapListHandler(),pid);
		Product product = new Product();
		Category category = new Category();
		for (Map<String, Object> map : list) {
			//将数据封装到product
			BeanUtils.populate(product, map);
			//将数据封装到category
			BeanUtils.populate(category, map);
			//将category封装到product
			product.setCategory(category);
		}
		return product;
	}

}
