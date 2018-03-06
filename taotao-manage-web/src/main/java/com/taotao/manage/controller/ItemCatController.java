package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 根据商品类目id查询商品类目
	 * @param categoryId 类目id
	 * @return
	 */
	@RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<ItemCat> queryItemDescByItemId(@PathVariable Long categoryId){
		
		try {
			ItemCat itemCat = itemCatService.queryById(categoryId);
			
			return ResponseEntity.ok(itemCat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

	
	/**
	 * 根据商品父类目id查询该类目的所有子类目
	 * @param parentId 父类目id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
			@RequestParam(value="id", defaultValue="0")Long parentId){
		
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId);
			List<ItemCat> list = itemCatService.queryByWhere(itemCat);
			
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 分页查询商品类目列表
	 * @param pageNo 页号
	 * @param rows 页大小
	 * @return 
	 */
	@RequestMapping(value="/query/{pageNo}", method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByPage(@PathVariable Integer pageNo, 
			@RequestParam(value = "rows", defaultValue = "20")Integer rows){
		
		try {
			//List<ItemCat> list = itemCatService.queryItemCatListByPage(pageNo, rows);
			List<ItemCat> list = itemCatService.queryByPage(null, pageNo, rows);
			
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果查询失败则返回http的状态码500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
