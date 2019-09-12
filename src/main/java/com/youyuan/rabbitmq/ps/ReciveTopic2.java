package com.youyuan.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class ReciveTopic2 {
	
	private static final String EXCHANGE_NAME="test_exchange_topic";
	
	private static final String ROUTING_KEY="order.#";
	
	private static final String QUEUE_NAME="test_exchange_topic_queue2";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接
		Connection connection=ConnectionUtil.getConnection();
		//创建channel
		final Channel channel=connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//队列绑定交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		//声明消费者应答生产者接收消息数
		channel.basicQos(1);
		//声明消费者
		DefaultConsumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				try{
					System.out.println("[2]消费者topic"+new String(body,"UTF-8"));
				}finally{
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//监听队列
		channel.basicConsume(QUEUE_NAME,false , consumer);
	}

}
