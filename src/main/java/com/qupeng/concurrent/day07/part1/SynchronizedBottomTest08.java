package com.qupeng.concurrent.day07.part1;

import java.util.concurrent.CountDownLatch;


/**
 * synchronized关键字的底层原理
 * 
 * 轻量级锁和重量级锁对比
 * -XX:BiasedLockingStartupDelay=0
 * @author qupeng
 */
public class SynchronizedBottomTest08 {
	
	private static CountDownLatch cdLatch=new CountDownLatch(1000000000);

	public static void main(String[] args) throws InterruptedException {
		
		final Test test=new Test();
		
		long beforeTime = System.currentTimeMillis();
		
		//创建两个线程去执行，这样就产生了竞争就是重量级锁
		for(int i=0;i<2;i++){
			new Thread(){
				
				@Override
				public void run() {
					//执行10亿次
					while(cdLatch.getCount()>0){
						test.testIncreament();
					}
				}
				
			}.start();
		}
		cdLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("用时是："+(endTime-beforeTime)+"毫秒");
		
	}
	
	static  class  Test{
		
		private int l=0;
		
		public synchronized void testIncreament(){
			l++;
			cdLatch.countDown();
		}
	}
}
