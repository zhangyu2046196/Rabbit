package com.youyuan.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Send {
	
	private static final String EXCHANGE_NAME="test_exchange_fanout";//交换机名称

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接
		Connection connection=ConnectionUtil.getConnection();
		//创建channel
		Channel channel=connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//fanout分发
		//push消息往交换机
		String msg="生产者往交换机push消息";
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		System.out.println("生产者将消息放到交换机"+msg);
		
		channel.close();
		connection.close();
	}

}
