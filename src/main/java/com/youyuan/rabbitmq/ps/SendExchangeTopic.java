package com.youyuan.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class SendExchangeTopic {
	
	private static final String EXCHANGE_NAME="test_exchange_topic";
	
	private static final String ROUTING_KEY="order.dao.add";

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
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//指定消费者在应答时发送给消费者数量
		channel.basicQos(1);
		for(int i=1;i<=50;i++){
			String msg=i+"生产者topic";
			//写入消息
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
			
			System.out.println("["+i+"]"+msg);
		}
	}

}
