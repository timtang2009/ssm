package com.fxt.test;

import com.alibaba.fastjson.JSON;
import com.fxt.pojo.User;
import com.mongodb.Mongo;

import redis.clients.jedis.Jedis;

import com.fxt.dao.UserMapper;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);
//  private ApplicationContext ac = null;  
    @Autowired  
    private UserMapper userService;
    
    private Jedis jedis;
    
    private Mongo mg;
  
//  @Before  
//  public void before() {  
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");  
//      userService = (IUserService) ac.getBean("userService");  
//  }
    
    @Before
    public void setup() {
    	jedis = new Jedis("204.13.67.249", 6379);
    	mg = new Mongo("204.13.67.249", 27017);
    }
  
    @Test
    public void test1() {  
        User user = userService.selectByPrimaryKey(1);
         //System.out.println(user.getUserName());
        // logger.info("值："+user.getUserName());  
        logger.info(JSON.toJSONString(user));
    }
    
    @Test
    public void test2() {
    	//jedis.set("name","xinxin");//向key-->name中放入了value-->xinxin  
    	System.out.println(jedis.get("name"));//执行结果：xinxin  
    }
    
    @Test
    public void testMongo() {
        for (String name : mg.getDatabaseNames()) {

            System.out.println("dbName: " + name);

        }
    }
    
    @Test
    public void TestExecutionListener() {
    	int i = 0;
    	while (i != 5) {
    		i++;
    		System.out.println(i);
    	}
    }
    
    @Test
    public void testabc() {
    	List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
    	List<Integer> list2 = Arrays.asList(1,2,4,5,6,7,8);
    	list.remove(2);
    	System.out.println(list);
    }
    
    @Test
    public void test21() {
    	toMD5("140");
    	
    }
    
    private void toMD5(String plainText) {
        try {
           //生成实现指定摘要算法的 MessageDigest 对象。
           MessageDigest md = MessageDigest.getInstance("MD5");  
           //使用指定的字节数组更新摘要。
           md.update(plainText.getBytes());
           //通过执行诸如填充之类的最终操作完成哈希计算。
           byte b[] = md.digest();
           //生成具体的md5密码到buf数组
           int i;
           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
             i = b[offset];
             if (i < 0)
               i += 256;
             if (i < 16)
               buf.append("0");
             buf.append(Integer.toHexString(i));
           }
           System.out.println("32位: " + buf.toString());// 32位的加密
           System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
        } 
        catch (Exception e) {
          e.printStackTrace();
        }
      }
}  