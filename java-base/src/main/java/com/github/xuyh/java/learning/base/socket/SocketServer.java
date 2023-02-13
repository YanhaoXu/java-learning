package com.github.xuyh.java.learning.base.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketServer {
  public static void main(String[] args) {
    try {
      try (ServerSocket server = new ServerSocket(8080)) {
        log.info("正在等待客户端连接...");
        Socket socket = server.accept();
        log.info("客户端已连接,IP地址为:{}", socket.getInetAddress().getHostAddress());
      }
    } catch (IOException e) {
      log.error("", e);
    }

  }
}
