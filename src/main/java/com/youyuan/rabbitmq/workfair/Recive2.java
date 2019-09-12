package com.youyuan.rabbitmq.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Recive2 {
	
	private static final String QUEUE_NAME="test_work_queue";

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TimeoutException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//建立连接
		Connection connection=ConnectionUtil.getConnection();
		//创建channel
		final Channel channel=connection.createChannel();
		//声明队列
		boolean durable=true;//是否持久化  true持久化  false  否
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		//消费者在应答生产者前最多接收消息数
		int prefetchCount=1;
		channel.basicQos(prefetchCount);
		//定义消费者
		DefaultConsumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("[2]消费消息"+new String(body,"UTF-8"));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					channel.basicAck(envelope.getDeliveryTag(), false);//消费者手动应答生产者
				}
			}
		};
		//创建监听
		boolean autoAck=false;//消费者应答生产者方式  true自动应答  false手动应答
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}

}
