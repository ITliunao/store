package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;

public class ProductServiceImp implements ProductService {

	ProductDao productdao = new ProductDaoImp();
	@Override
	public List<Product> findHots() throws Exception {
		
		return productdao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		// TODO Auto-generated method stub
		return  productdao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		
		return productdao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsWithCidAndPage(String cid, int curNum) throws Exception {
		
		//查询总页数
		int totalRecords = productdao.findTotalRecords(cid);
		
		//PageModel
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		
		List list = productdao.findProductsWithCidAndPage(cid, pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		pm.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		
		
		return pm;
	}

}
