package com.github.xuyh;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumeDirectMQ {

  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    // 设定连接信息
    factory.setHost("localhost");
    // 5672是amqp协议接口
    factory.setPort(5672);
    factory.setUsername("admin");
    factory.setPassword("admin");
    factory.setVirtualHost("/test");

    // 不使用try-with-resource, 因为消费者是一直等待新的消息到来, 然后按照
    // 我们设定的逻辑进行处理, 所以这里不能在定义完成之后就关闭连接
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    // 创建一个基本的消费者
    channel.basicConsume("yyds", false, (s, delivery) -> {
      System.out.println(new String(delivery.getBody()));
    }, s -> {
    });



  }
}
