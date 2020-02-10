package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * -XX:BiasedLockingStartupDelay=0
 * 证明：
 * 偏向锁的含义不是只有两个线程在交替执行。
 * 
 * 我认为：不管是多少个线程去执行，只要是没有产生竞争关系
 * 就不会膨胀为轻量级锁，但是这个是有一些前提的，后面讲解。
 * 
 * 结论：不是网上说的只要有第三个线程去执行了且没有产生竞争关系
 * 时就会膨胀为轻量级锁。
 * @author qupeng
 */
public class SynchronizedBottomTest03 {
	
	public static void main(String[] args) throws InterruptedException {

		final Object  testLock=new Object();


		final Thread t1 = new Thread("线程1："){

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
		//保证t2在执行时不会和t1发生竞争
		t1.join();

		Thread t2 = new Thread("线程2："){

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
		t2.start();
		t2.join();

		Thread t3 = new Thread("线程3："){

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
		t3.start();


//		t3.join();
//		System.out.println("主线程：--------------------------加锁前----------------------------");
//		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//		synchronized (testLock) {
//			System.out.println("主线程：--------------------------加锁后----------------------------");
//			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//		}

	}

}
