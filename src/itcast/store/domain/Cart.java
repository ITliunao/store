package cn.itcast.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 购物车的封装
 *
 *2个属性: 个数不确定的购物项   总计
 *
 *3个方法 : 添加到购物车  移除购物项  清空购物车  
 *
 */
public class Cart {

	//2个属性: 个数不确定的购物项   总计
	
	Map<String , CartItem> map = new HashMap<String , CartItem>();
	
	private double total ;
	
	


	//	3个方法 : 添加到购物车  移除购物项  清空购物车  
//	 添加到购物车
	public void addCartItemToCart(String pid , CartItem cartitem ){
		
		//如果集合中没有此购物项 直接添加值
		if(!map.containsKey(pid)){
			
			map.put(pid, cartitem);
		}else{
		//集合中有此购物项 , 修改数量值

			CartItem oldcart = map.get(pid);
			
			oldcart.setNums(cartitem.getNums() + oldcart.getNums());
			map.put(pid, oldcart);
			
		}
		
	}
	
	
//	移除购物项
	public void removeCart(String pid){

		 map.remove(pid);
	}
	
	
//	清空购物车  
	public void clearCart(){
		map.clear();
	}
	
	
	
	public Map<String, CartItem> getMap() {
		return map;
	}


	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}


	//为了方便遍历MAP中的所有的购物项,提供以下方法
	public  Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	public double getTotal() {
		
		//清零
		total = 0;
		//获取map中的所有购物项
		Collection<CartItem> values = map.values();
		
		//遍历计算获取总计
		for (CartItem cartItem : values) {
			total += cartItem.getTotal();
		}
		
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}
}
