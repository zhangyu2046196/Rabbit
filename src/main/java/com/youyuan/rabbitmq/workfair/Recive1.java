package com.youyuan.rabbitmq.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Recive1 {
	
	private static final String QUEUE_NAME="test_work_queue";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//建立连接
		Connection connection=ConnectionUtil.getConnection();
		//创建channel
		final Channel channel=connection.createChannel();
		//声明队列
		boolean durable=true;//是否持久化  true持久化  false否
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		//消费者在应答生产者前每次接收几个消息
		int prefetchCount=1;//每次接收消息数
		channel.basicQos(prefetchCount);
		
		//定义消费者
		DefaultConsumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("[1]消费信息"+new String(body,"UTF-8"));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					channel.basicAck(envelope.getDeliveryTag(), false);//手动应答给生产者
				}
			}
		};
		//创建监听
		boolean autoAck=false;//消费者应答生产者方式  true自动应答  false手动应答
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}

}
