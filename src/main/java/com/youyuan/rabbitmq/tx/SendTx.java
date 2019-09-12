package com.youyuan.rabbitmq.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.youyuan.rabbitmq.util.ConnectionUtil;

public class SendTx {
	
	private static final String QUEUE_NAME="test_tx_queue";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接
		Connection connection = ConnectionUtil.getConnection();
		//创建channel
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		try{
			String msg="消息确认机制—事务";
			//声明事务
			channel.txSelect();
			//发布消息
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
			System.out.println("send_tx:"+msg);
			//提交事务
			int x=10000/0;
			channel.txCommit();
		}catch(Exception e){
			e.printStackTrace();
			//回滚事务
			channel.txRollback();
			System.out.println("send tx 回滚");
		}
		
		channel.close();
		connection.close();
	}

}
