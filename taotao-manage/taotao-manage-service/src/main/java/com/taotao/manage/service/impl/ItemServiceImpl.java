package com.taotao.manage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public Long saveItem(Item item, String desc) {
		// 1、保存基本信息
		saveSelective(item);

		// 2、保存描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insertSelective(itemDesc);

		// 3、返回商品id
		return item.getId();
	}

	@Override
	public void updateItem(Item item, String desc) {
		// 1、保存基本信息
		updateSelective(item);

		// 2、保存描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public DataGridResult queryItemListByTitle(String title, Integer page, Integer rows) {
		//设置分页
		PageHelper.startPage(page, rows);
		
		Example example = new Example(Item.class);
		
		//添加查询条件
		if(StringUtils.isNotBlank(title)) {
			try {
				Criteria criteria = example.createCriteria();
				title = URLDecoder.decode(title, "utf-8");
				criteria.andLike("title", "%" + title + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		//根据更新时间降序排
		example.orderBy("updated").desc();
		
		//查询
		List<Item> list = itemMapper.selectByExample(example);
		
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
