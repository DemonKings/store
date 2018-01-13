package com.itheima.service;

import java.util.List;

import com.itheima.domain.Product;

public interface ProductService {

	List<Product> findHotPro() throws Exception;

	List<Product> findNewPro() throws Exception;

	Product findByPid(String pid) throws Exception;

}
