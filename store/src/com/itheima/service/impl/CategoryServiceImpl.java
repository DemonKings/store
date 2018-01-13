package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itheima.dao.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.service.CategoryService;
import com.itheima.utils.Constant;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	@Override
	/**
	 * 查询所有商品分类
	 */
	public String findAll() throws Exception {
		/*//调用dao查询
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findAll();
		//使用fastjson转换给json串
		String json = JSON.toJSONString(list); */
		
		//从连接池获取jedis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		//尝试获取jedis中存放的商品分类json串
		String json = jedis.get(Constant.CATEGORY_LIST_JSON);
		//判断json是否为空
		if(json==null){
			//调用dao查询
			CategoryDao dao = new CategoryDaoImpl();
			List<Category> list = dao.findAll();
			//使用fastjson转换给json串
			json = JSON.toJSONString(list);
			//将json串放入jedis中
			jedis.set(Constant.CATEGORY_LIST_JSON, json);
		}
		return json;
	}

}
