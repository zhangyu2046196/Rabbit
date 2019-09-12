package com.youyuan.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * rabbitmq连接工具类
 * @author zhangyu
 * @date 2018-4-18 下午5:28:23
 */
public class ConnectionUtil {

	/**
	 * 获取连接
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Connection getConnection() throws IOException, TimeoutException{
		//创建连接工厂
		ConnectionFactory factory=new ConnectionFactory();
		//设置服务器Ip
		factory.setHost("127.0.0.1");
		//设置端口号
		factory.setPort(5672);
		//设置vhost(数据库名字)
		factory.setVirtualHost("/zhangyu");
		//设置用户名
		factory.setUsername("zhangyu");
		//设置密码
		factory.setPassword("123456");
		return factory.newConnection();
	}
}
