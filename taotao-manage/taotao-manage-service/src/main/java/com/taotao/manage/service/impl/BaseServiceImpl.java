package com.taotao.manage.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class BaseServiceImpl<T extends BasePojo> implements BaseService<T>{
	
	@Autowired //在spring 4.x 版本之后可以使用泛型依赖注入；而且业务Mapper（UserMapper）要继承Mapper
	private Mapper<T> mapper;
	
	private Class<T> clazz;
	
	public BaseServiceImpl() {
		//this表示当前被实例化的对象；如UserServiceImpl--->BaseServiceImpl<User>
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}
	
	@Override
	public T queryById(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> queryByWhere(T t) {
		return mapper.select(t);
	}

	@Override
	public long queryCountByWhere(T t) {
		return mapper.selectCount(t);
	}

	@Override
	public List<T> queryByPage(T t, Integer page, Integer rows) {
		//设置分页
		PageHelper.startPage(page, rows);
		
		return mapper.select(t);
	}

	@Override
	public void saveSelective(T t) {
		if(t.getCreated() == null) {
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		} else if(t.getUpdated() == null) {
			t.setUpdated(new Date());
		}
		mapper.insertSelective(t);
	}

	@Override
	public void updateSelective(T t) {
		if(t.getUpdated() == null) {
			t.setUpdated(new Date());
		}
		mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		Example example = new Example(clazz);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIn("id", Arrays.asList(ids));
		mapper.deleteByExample(example);
	}

}
