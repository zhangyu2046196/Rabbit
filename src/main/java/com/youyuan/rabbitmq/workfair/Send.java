package com.youyuan.rabbitmq.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Send {
	
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
		Channel channel=connection.createChannel();
		//声明队列
		boolean durable=true;//是否持久化  true持久化    false否
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		//声明每次给消费者发送消息数,在消费者消费完应答前不继续分发,只有接到消费者消费完的应答才继续分发
		int prefetchCount=1;//每次向消费者发送消息数量
		channel.basicQos(prefetchCount);
		
		for(int i=1;i<=50;i++){
			String msg=i+"生产者push消息";
			channel.basicPublish("", QUEUE_NAME,null, msg.getBytes());
			System.out.println(i+"生产者push消息");
			try {
				Thread.sleep(i*5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		channel.close();
		connection.close();
	}

}
