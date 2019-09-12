package com.youyuan.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.youyuan.rabbitmq.util.ConnectionUtil;

/**
 * 简单队列  消费者
 * @author zhangyu
 * @date 2018-4-18 下午5:54:02
 */
public class Recive {

	private static final String QUEUE_NAME="test_simple_queue";
	
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
		//声明队列 
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//创建消费者
		DefaultConsumer dConsumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				// TODO Auto-generated method stub
				super.handleDelivery(consumerTag, envelope, properties, body);
				System.out.println("新api  接收内容信息:"+new String(body,"UTF-8"));
			}
		};
		
		//创建监听
		channel.basicConsume(QUEUE_NAME, dConsumer);
		
		//channel.close();
		//connection.close();
	}

}
