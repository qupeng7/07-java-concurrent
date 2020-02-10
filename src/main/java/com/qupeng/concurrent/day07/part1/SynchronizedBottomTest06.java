package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 
 * 证明：
 * 偏向锁偏向一个线程后，不会发生重偏向
 * 另一个线程的情况，只会膨胀为轻量级锁。
 * 注意：这里因为是主线程去进入同步代码区域
 * 所以会膨胀为轻量级锁
 * -XX:BiasedLockingStartupDelay=0
 * @author qupeng
 */
public class SynchronizedBottomTest06 {

	public static void main(String[] args) throws InterruptedException {
		final Object  testLock=new Object();
		
		final Thread t1 = new Thread("子线程："){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName+"--------------------------加锁前----------------------------");
				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
				synchronized (testLock) {
					System.out.println(threadName+"--------------------------加锁后----------------------------");
					System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
				}
			}
		};
		t1.start();
		t1.join();
		
		System.out.println("主线程：------------------------------加锁之前--------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		synchronized (testLock) {
			System.out.println("主线程：-------------------------------加锁之后------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		}
	}

}
