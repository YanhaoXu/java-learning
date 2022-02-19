package com.github.xuyh.java.learning.multithread.startingthread.implementscallable;

import java.util.concurrent.*;

public class Application {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<Boolean> task = executorService.submit(new Runner());
    System.out.println("task.get() = " + task.get());
  }
}

class Runner implements Callable<Boolean> {

  @Override
  public Boolean call() throws Exception {
    for (int i = 0; i < 5; i++) {
      System.out.println("Hello: " + i);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
    return true;
  }
}
