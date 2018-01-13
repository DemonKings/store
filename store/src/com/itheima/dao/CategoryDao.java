package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

	List<Category> findAll() throws SQLException;

}
