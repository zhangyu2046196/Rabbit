package com.youyuan.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Recive2 {
	
	private static final String QUEUE_NAME="test_work_queue1";

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
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//创建消费者
		DefaultConsumer consumer= new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("[2] Revice msg:"+new String(body,"UTF-8"));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					System.out.println("[2] ......");
				}
			}
		};
		//创建监听
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
