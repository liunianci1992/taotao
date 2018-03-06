package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//TODO 删除
	//TODO 上架
	//TODO 下架
	
	/**
	 * 根据商品标题模糊分页查询商品列表
	 * @param title 标题
	 * @param page 页号
	 * @param rows 页大小
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryItemListByTitle(@RequestParam(value = "title", required = false)String title,
			@RequestParam(value="page", defaultValue = "1")Integer page, 
			@RequestParam(value = "rows", defaultValue = "30")Integer rows){
		try {
			DataGridResult dataGridResult = itemService.queryItemListByTitle(title, page, rows);
			
			return ResponseEntity.ok(dataGridResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 保存商品基本、描述信息到数据库中
	 * @param item 基本信息
	 * @param desc 描述信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, String desc){
		
		try {
			Long itemId = itemService.saveItem(item, desc);
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 更新商品基本、描述信息到数据库中
	 * @param item 基本信息
	 * @param desc 描述信息
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item, String desc){
		
		try {
			itemService.updateItem(item, desc);
			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
