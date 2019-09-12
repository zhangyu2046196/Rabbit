package com.youyuan.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class Send {
	
	private static final String QUEUE_NAME="test_work_queue1";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//创建连接
		Connection connection=ConnectionUtil.getConnection();
		//创建通道
		Channel channel=connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//向队列放入消息
		for(int i=1;i<=50;i++){
			String msg=i+"放入消息";
			channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
			System.out.println(i+" send ");
			Thread.sleep(i*20);
		}
	}

}
