package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 单个重偏向没有意义，因为既然要进行CAS去将
 * 对象头的数据改为另一个线程信息，那还不如直接将
 * 其升级为轻量级锁。（因为偏向锁的意义就在于将一个
 * 线程的信息存入对象头后，后面若干次进行复用，整个过程
 * 只进行一次CAS，后面要判断同步只需在JVM内部去进行一个
 * if即可，不用再去进行CAS耗费那么多功夫）
 * -XX:BiasedLockingStartupDelay=30000
 * 
 * 轻量级锁退出同步代码区域后锁的状态？
 * 轻量级锁的线程退出同步区域后锁的状态为无锁，
 * 因为轻量级锁的加锁过程需要将原有偏向锁或者轻量级锁
 * 的信息进行替换（偏向锁的撤销--->加上轻量级锁）
 * 
 * 那么进行了锁的撤销（清空线程信息）下一次另一个线程来会不会
 * 重偏向呢？
 * 不会，一个锁对象只能偏向一个线程，不能发生重偏向，
 * 另一个线程再来访问同步区域：
 * 1、原偏向锁线程没有死亡，那么膨胀为轻量级锁；
 * 2、原偏向锁线程已死亡但本线程为原有线程的主线程，
 * 那么也膨胀为轻量级锁。
 * 
 * @author qupeng
 */
public class SynchronizedBottomTest11 {
	

	public static void main(String[] args) throws InterruptedException {
		Thread mainThread = Thread.currentThread();
		mainThread.setName("主线程：");
		System.out.println("--------------------------加锁前------------------------------------");
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
				System.out.println(threadName+"--------------------------退出同步代码块后----------------------------");
				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
				
//				try {Thread.sleep(2000);} catch (InterruptedException e) {}
//				System.out.println(threadName+"--------------------------第二次加锁前----------------------------");
//				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//				synchronized (testLock) {
//					System.out.println(threadName+"--------------------------第二次加锁后----------------------------");
//					System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//				}
				
			}
		};
		t1.start();
		t1.join();
		
		
		Thread.sleep(4000);
		System.out.println("主线程：------------------------------加锁之前--------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		synchronized (testLock) {
			System.out.println("主线程：-------------------------------加锁之后------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		}
		
	}
	
}
