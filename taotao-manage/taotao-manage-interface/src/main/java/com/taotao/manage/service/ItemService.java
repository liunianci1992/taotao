package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item> {

	/**
	 * 保存商品基本、描述信息到数据库中
	 * @param item 基本信息
	 * @param desc 描述信息
	 * @return
	 */
	Long saveItem(Item item, String desc);

	void updateItem(Item item, String desc);

	/**
	 * 根据商品标题模糊分页查询商品列表
	 * @param title 标题
	 * @param page 页号
	 * @param rows 页大小
	 * @return
	 */
	DataGridResult queryItemListByTitle(String title, Integer page, Integer rows);

}
