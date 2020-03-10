package com.zst.test.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @description: 用 FutureTask 就不用接收 submit 方法的返回值了
 * @author: Zhoust
 * @date: 2020/01/29 上午9:02
 * @version: V1.0
 */
@Slf4j
public class FutureTaskDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CalculateTask calculateTask = new CalculateTask(1000);
        log.info("主线程提交任务");
        FutureTask<Integer> futureTask = new FutureTask<>(calculateTask);
        executorService.submit(futureTask);
        //Future<Integer> resultFuture = executorService.submit(calculateTask); 直接用 Callable 还得接收返回值
        //拒绝接收新任务，但是会让已提交的任务执行结束（该方法并不阻塞）
        executorService.shutdown();

        Integer result;
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                boolean done = futureTask.isDone();
                log.info("子线程是否完成任务：{}", done);
                if (done) {
                    result = futureTask.get();
                    break;
                }
            }
            catch (ExecutionException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        log.info("主线程得到执行结果 {}", result);
        log.info("线程池是否结束 {}", executorService.isShutdown());
    }


    /**
     * 计算从 1 累加到 count 的结果
     */
    static class CalculateTask implements Callable<Integer> {

        private Integer count;

        public CalculateTask(Integer count) {
            this.count = count;
        }

        @Override
        public Integer call() throws Exception {
            TimeUnit.SECONDS.sleep(2);
            int result = 0;
            for (int i = 0; i <= count; i ++) {
                result += i;
            }
            return result;
        }

    }


}