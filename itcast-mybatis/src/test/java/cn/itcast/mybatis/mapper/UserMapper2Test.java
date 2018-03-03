package cn.itcast.mybatis.mapper;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Date;
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
import cn.itcast.mybatis.pojo.User2;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class UserMapper2Test {

	private UserMapper2 userMapper2;

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		// 利用Resources工具类获取总配置文件的输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 利用sqlSessionFacotoryBuilder创建sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		MapperHelper mapperHelper = new MapperHelper();
		// 特殊配置
		Config config = new Config();
		// 设置配置
		mapperHelper.setConfig(config);
		// 注册自己项目中使用的通用Mapper接口，这里没有默认值，必须手动注册
		mapperHelper.registerMapper(Mapper.class);
		// 配置完成后，执行下面的操作
		mapperHelper.processConfiguration(sqlSession.getConfiguration());

		userMapper2 = sqlSession.getMapper(UserMapper2.class);
	}

	@Test
	public void testSelectOne() {
		// 查询的数据必须只能一个，如果有多个则报错
		User2 param = new User2();
		param.setAccount("lilei");
		User2 user = userMapper2.selectOne(param);
		System.out.println(user);
	}

	@Test
	public void testSelectAll() {
		List<User2> list = userMapper2.selectAll();
		for (User2 user2 : list) {
			System.out.println(user2);
		}
	}

	@Test
	public void testSelectByPrimaryKey() {
		User2 user2 = userMapper2.selectByPrimaryKey(2L);
		System.out.println(user2);
	}

	@Test
	public void testInsertSelective() {
		// 选择性新增；只对对象中有值的属性进行加入到insert语句中
		User2 param = new User2();
		param.setAccount("itcast");
		param.setAge(12);
		param.setBirthday(new Date());
		param.setUserName("传智播客");
		userMapper2.insertSelective(param);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		// 选择性更新；只对对象中有值的属性进行加入到update语句中
		User2 param = new User2();
		param.setId(7L);
		param.setAccount("heima");
		param.setUserName("黑马");
		userMapper2.updateByPrimaryKeySelective(param);
	}

	@Test
	public void testDeleteByPrimaryKey() {
		userMapper2.deleteByPrimaryKey(7L);
	}

	/**
	 * 分页查询年龄大于等于14岁的男性用户并且根据年龄降序排序；查询第2页，每页2条
	 */
	@Test
	public void testSelectByExample() {
		// 分页设置
		PageHelper.startPage(2, 2);

		// 创建查询对象
		Example example = new Example(User2.class);

		// 创建查询条件对象
		Criteria criteria = example.createCriteria();
		// 男性用户
		criteria.andEqualTo("gender", 1);

		// 年龄大于等于14岁
		criteria.andGreaterThanOrEqualTo("age", 14);

		// 设置排序
		example.orderBy("age").desc();

		// 查询
		List<User2> list = userMapper2.selectByExample(example);

		// 构造分页信息
		PageInfo<User2> pageInfo = new PageInfo<>(list);

		System.out.println("总记录数为：" + pageInfo.getTotal() + "；总页数为：" + pageInfo.getPages() + "；当前页号为："
				+ pageInfo.getPageNum() + "；页大小为：" + pageInfo.getPageSize());

		for (User2 user : pageInfo.getList()) {
			System.out.println(user);
		}
	}

}
