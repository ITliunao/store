package cn.itcast.store.domain;

import java.util.List;

/**
 * 
 * 购物项的封装类
 *
 */
		
public class CartItem {

	private Product product;  // 商品携带3个参数(图片路径,商品名称,商品价格) 
	
	private int nums ;    //当前商品的个数
	
	private double total; //小计
	
	
	public double getTotal (){
		
		return product.getShop_price()*nums;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public int getNums() {
		return nums;
	}


	public void setNums(int nums) {
		this.nums = nums;
	}
	
}
