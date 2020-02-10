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
 * -XX:BiasedLockingStartupDelay=0
 * 
 * 偏向锁的线程退出同步区域后锁的状态？
 * 偏向锁的线程退出同步区域后锁的状态依然为偏向锁（偏向之前偏向的线程）
 * 但是
 * @author qupeng
 */
public class SynchronizedBottomTest10 {
	

	public static void main(String[] args) throws InterruptedException {
		Thread mainThread = Thread.currentThread();
		mainThread.setName("主线程：");
		System.out.println("--------------------------加锁前------------------------------------");
		final Object  testLock=new Object();
		
		final Thread t1 = new Thread("子线程1："){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
//				final Thread t1_1 = new Thread("子线程1中的子线程："){
//					
//					@Override
//					public void run() {
//						String threadName = Thread.currentThread().getName();
//						System.out.println(threadName+"--------------------------加锁前----------------------------");
//						System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//无锁
//						synchronized (testLock) {
//							System.out.println(threadName+"--------------------------加锁后----------------------------");
//							System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//偏向锁
//						}
//						System.out.println(threadName+"--------------------------退出同步代码块后----------------------------");
//						System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//偏向锁
//					}
//				};
//				t1_1.start();
//				try {
//					t1_1.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
				System.out.println(threadName+"--------------------------加锁前----------------------------");
				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//可偏向状态，但还没有偏向
				synchronized (testLock) {
					System.out.println(threadName+"--------------------------加锁后----------------------------");
					System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//偏向锁
				}
				System.out.println(threadName+"--------------------------退出同步代码块后----------------------------");
//				System.out.println(threadName+"--------------------------退出同步代码块后休眠一段时间表明t1还活着----------------------------");
//				try {
//					Thread.sleep(10_000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(threadName+"--------------------------休眠结束后----------------------------");
				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//偏向锁
			}
		};
		t1.start();
		t1.join();
//		Thread.sleep(7000);
		
		
		
		final Thread t2 = new Thread("子线程2："){
			
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
			}
		};
		t2.start();
		
		
		
		
//		System.out.println("主线程：------------------------------加锁之前--------------------------------");
//		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//		synchronized (testLock) {
//			System.out.println("主线程：-------------------------------加锁之后------------------------------");
//			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
//		}
//		System.out.println("主线程：--------------------------退出同步代码块后----------------------------");
//		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		
	}
	
}
