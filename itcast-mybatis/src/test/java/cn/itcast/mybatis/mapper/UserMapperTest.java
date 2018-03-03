package cn.itcast.mybatis.mapper;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.mybatis.pojo.User;

public class UserMapperTest {
	
	private UserMapper userMapper;

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		//利用Resources工具类获取总配置文件的输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//利用sqlSessionFacotoryBuilder创建sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		userMapper = sqlSession.getMapper(UserMapper.class);
	}

	@Test
	public void testQueryUsers() {
		List<User> list = userMapper.queryUsers();
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testQueryUsersInPage() {
		//设置分页；对应紧接着执行的查询语句生效
		PageHelper.startPage(2, 2);
		
		List<User> list = userMapper.queryUsers();
		
		//构造分页信息
		PageInfo<User> pageInfo = new PageInfo<>(list);
		
		System.out.println("总记录数为：" + pageInfo.getTotal() + "；总页数为：" + pageInfo.getPages() + 
		"；当前页号为：" + pageInfo.getPageNum() +  "；页大小为：" + pageInfo.getPageSize());
		
		for (User user : pageInfo.getList()) {
			System.out.println(user);
		}
	}

	@Test
	public void testDeleteByIds() {
		Long[] ids = {7L, 8L};
		userMapper.deleteByIds(ids);
	}

}
