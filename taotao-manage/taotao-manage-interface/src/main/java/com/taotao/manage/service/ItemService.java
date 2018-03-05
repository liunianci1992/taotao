package com.taotao.manage.service;

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

}
