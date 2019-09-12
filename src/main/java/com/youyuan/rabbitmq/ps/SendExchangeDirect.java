package com.youyuan.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class SendExchangeDirect {
	
	private static final String EXCHANGE_NAME="test_exchange_direct";
	
	private static final String ROUTING_KEY="e_direct";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection connection=ConnectionUtil.getConnection();
		//创建channel
		Channel channel=connection.createChannel();
		
		channel.confirmSelect();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//指定消费者应答时最多发送几条消息
		channel.basicQos(1);
		
		//发送消息到交换机,交换机不存储消息,队列存储消息
		for(int i=1;i<=50;i++){
			String msg="消费者测试交换机类型direct";
			msg="["+i+"]"+msg;
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
			System.out.println(msg);
		}
		channel.close();
		connection.close();
	}

}
