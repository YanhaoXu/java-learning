package com.github.xuyh.java.learning.multithread.syncrhonized;

public class Worker {

  private int count = 0;

  public synchronized void increment() {
    count++;
  }

  public static void main(String[] args) {
    Worker worker = new Worker();
    worker.run();
  }

  public void run() {
    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        increment();
      }
    });
    thread1.start();

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        increment();
      }
    });
    thread2.start();


    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    System.out.println("count = " + count);

  }

}
