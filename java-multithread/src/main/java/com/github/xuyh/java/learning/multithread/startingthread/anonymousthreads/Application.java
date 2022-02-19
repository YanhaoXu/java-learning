package com.github.xuyh.java.learning.multithread.startingthread.anonymousthreads;

public class Application {
  public static void main(String[] args) {
    Thread thread = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Hello: " + i);
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
      }
    });

    thread.start();
  }
}
