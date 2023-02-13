package com.github.xuyh.java.learning.base.socket;

import java.io.IOException;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketClient {
  public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 8080)) {
      log.info("已连接到服务端!");
    } catch (IOException e) {
      log.info("服务端连接失败!");
      log.error("", e);
    }
  }
}
