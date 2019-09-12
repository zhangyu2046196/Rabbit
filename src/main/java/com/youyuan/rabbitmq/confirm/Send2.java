package com.youyuan.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.youyuan.rabbitmq.util.ConnectionUtil;

/**
 * 消息确认机制  confirm  批量发送,回执是一条,只要有一条失败就都失败
 * @author zhangyu
 * @date 2018-4-26 下午9:22:09
 */
public class Send2 {
	
	private static final String EXCHANGE_NAME="test_exchange_confirm";

	/**
	 * @param args
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//创建连接
		Connection connection = ConnectionUtil.getConnection();
		//创建channel
		Channel channel=connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		//声明消息确认confirm模式
		
		channel.confirmSelect();
		//发送消息
		String msg="批量发送confirm确认";
		for(int i=1;i<=50;i++){
			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		}
		System.out.println("send confirm msg"+msg);
		
		//接收confirm确认回执
		if(channel.waitForConfirms()){
			System.out.println("批量消息发送成功");
		}else{
			System.out.println("批量消息发送失败");
		}
		
		channel.close();
		connection.close();
	}

}
