package com.itheima.service;

import java.util.List;

import com.itheima.domain.Product;
import com.itheima.utils.PageBean;

public interface ProductService {

	List<Product> findHotPro() throws Exception;

	List<Product> findNewPro() throws Exception;

	Product findByPid(String pid) throws Exception;

	PageBean<Product> findByPage(String cid, int pageNumber, int pageSize) throws Exception;

}
