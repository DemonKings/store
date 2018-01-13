package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Product;

public interface ProductDao {

	List<Product> findHotPro() throws SQLException;

	List<Product> findNewPro() throws SQLException;

	Product findByPid(String pid) throws Exception;

	int findTotalCount(String cid) throws Exception;

	List<Product> findByCid(String cid, int startIndex, int pageSize) throws Exception;

}
