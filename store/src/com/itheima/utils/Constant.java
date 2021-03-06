package com.itheima.utils;

public interface Constant {
	//激活
	int IS_ACTIVE = 1;
	//未激活
	int NOT_ACTIVE = 0;
	//是热门
	int PRODUCT_IS_HOT = 1;
	//不是热门
	int PRODUCT_NOT_HOT = 0;
	//下架
	int PRODUCT_IS_DOWN = 1;
	//未下架
	int PRODUCT_NOT_DOWN = 0;
	//热门和最新商品展示条数
	int NUM_OF_HOT = 9;
	//商品分类下每页展示条数
	int PRODUCT_PAGESIZE = 12;
	//存放在Redis中的商品分类json串的key
	String CATEGORY_LIST_JSON = "CATEGORYLISTJSON";
	/*
	 * 订单状态
	 */
	//未支付
	int ORDER_WEIZHIFU = 1;
	//未发货
	int ORDER_WEIFAHUO = 2;
	//已发货
	int ORDER_YIFAHUO = 3;
	//已签收
	int ORDER_YIQIANSHOU = 4;
	//我的订单每页显示条数
	int ORDER_PAGESIZE = 3;
}
