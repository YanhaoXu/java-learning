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
      // basicAck是确认应答,第一个参数是当前消息的标签,后面的参数是
      // 是否批量处理消息队列中所有的消息,如果为false表示的只处理当前消息
      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      //basicNack是拒绝应达,最后一个参数表示是否是将当前消息放回队列
      // 如果为false,那么消息就会被丢弃
      //      channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
      // 和上面一样,最后一个参数为false,这里省略了
      //      channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
    }, s -> {
    });



  }
}
