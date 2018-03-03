package cn.itcast.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.itcast.mybatis.pojo.User;

public interface UserMapper {

	public List<User> queryUsers();
	
	public void deleteByIds(@Param("ids")Long[] ids);
}
