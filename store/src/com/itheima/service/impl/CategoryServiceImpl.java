package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itheima.dao.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Override
	/**
	 * 查询所有商品分类
	 */
	public String findAll() throws Exception {
		//调用dao查询
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findAll();
		//使用fastjson转换给json串
		String json = JSON.toJSONString(list);
		return json;
	}

}
