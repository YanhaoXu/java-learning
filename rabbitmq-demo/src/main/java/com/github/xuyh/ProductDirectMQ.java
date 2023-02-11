package com.github.xuyh;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductDirectMQ {

  public static void main(String[] args) {

    ConnectionFactory factory = new ConnectionFactory();

    // 设定连接信息
    factory.setHost("localhost");
    // 5672是amqp协议接口
    factory.setPort(5672);
    factory.setUsername("admin");
    factory.setPassword("admin");
    factory.setVirtualHost("/test");

    try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel();) {
      // 声明队列,如果此队列不存在,会自动创建
      channel.queueDeclare("yyds", false, false, false, null);
      // 将队列绑定到交换机
      channel.queueBind("yyds", "amq.direct", "my-yyds");
      // 发布新的消息.注意消息需要转换为byte[]
      channel.basicPublish("amq.direct", "my-yyds", null, "Hello World!".getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
