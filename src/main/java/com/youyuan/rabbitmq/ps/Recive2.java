package com.youyuan.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Recive2 {
	
	private static final String QUEUE_NAME="test_exchange_queue_sms";//声明队列名称
	
	private static final String EXCHANGE_NAME="test_exchange_fanout";//声明交换机名称
	
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
		//声明队列
		boolean durable=true;//是否持久化   true持久化  false否
		channel.queueDeclare(QUEUE_NAME,durable , false, false, null);
		
		//将队列绑定到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		//创建消费者
		DefaultConsumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("[2]消费者消费消息"+new String(body,"UTF-8"));
			}
		};
		//监听队列
		channel.basicConsume(QUEUE_NAME, consumer);
	}

}
