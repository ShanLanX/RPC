package com.swx.rpc.client.transport;

import java.util.concurrent.*;

public class RpcFuture<T> implements Future<T> {
    /**
     * 响应结果
     */
    private T response;

    private CountDownLatch countDownLatch=new CountDownLatch(1);


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    /**
     * 是否完成的函数，响应数据不为空表示已经完成
     * @return
     */

    @Override
    public boolean isDone() {
        return response!=null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        /**
         * 线程阻塞，countdownlatch的减少到0
         */
        countDownLatch.await();

        return response;
    }

    /**
     * 超时等待
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(countDownLatch.await(timeout, unit)){
            return response;
        }
        return null;
    }

    /**
     * 设置响应
     * @param response
     */
    public void setResponse(T response){
        this.response=response;
        countDownLatch.countDown();
    }
}
