package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;

public class CategoryServiceImp implements CategoryService {

	@Override
	public List<Category> getAllCates() throws Exception {

		CategoryDao dao = new CategoryDaoImp();
		
		return dao.getAllCates();
	}

}
