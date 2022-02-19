package com.github.xuyh.java.learning.multithread.startingthread.extendingthread;

public class Application {
  public static void main(String[] args) {
    Runner runner1 = new Runner();
    runner1.start();
    Runner runner2 = new Runner();
    runner2.start();
  }
}


class Runner extends Thread {
  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println("Hello: " + i);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }
}
