package com.youyuan.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

/**
 * 简单队列   生产者
 * @author zhangyu
 * @date 2018-4-18 下午5:37:53
 */
public class Send {
	
	private static final String QUEUE_NAME="test_simple_queue";//定义队列(类似数据库中的表名)

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection connection=ConnectionUtil.getConnection();
		//创建通道
		Channel channel= connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String msg="北京您好，有缘youyuan";
		//发送数据
		channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
		
		channel.close();
		connection.close();
	}

}
