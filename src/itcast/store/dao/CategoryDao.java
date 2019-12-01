package cn.itcast.store.dao;

import java.util.List;

import cn.itcast.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCates() throws Exception;

}
