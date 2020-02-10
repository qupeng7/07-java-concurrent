package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;


/**
 * synchronized关键字的底层原理
 * -XX:BiasedLockingStartupDelay=30000
 * 
 * 重量级锁退出同步代码区域后锁的状态？
 * 
 * 重量级锁退出同步代码区域后不会对MarkWord
 * 做任何操作，不存在锁撤销了，
 * 重量级锁始终是保存的操作系统互斥量
 * monitor的信息，不会去保存当前线程的信息。
 * 
 * 
 * 等所有线程都退出了同步区域时，不会对MarkWord
 * 做任何操作，不存在锁撤销了。
 * 
 * 结论：锁的膨胀过程不可逆。
 * 
 * @author qupeng
 */
public class SynchronizedBottomTest12 {
	

	public static void main(String[] args) throws InterruptedException {
		Thread mainThread = Thread.currentThread();
		mainThread.setName("主线程：");
		final Test testLock=new Test();
		System.out.println("--------------------------加锁前------------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//无锁
		new Thread("子线程："){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				synchronized (testLock) {
					System.out.println(threadName+"---------------------------------加锁后----------------------------");
					System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//轻量级锁
					//休眠3秒
					try {Thread.sleep(3_000);} catch (InterruptedException e) {}
				}
				System.out.println(threadName+"---------------------------------释放了锁！----------------------------");
				System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//主线程还没有释放锁，所以是重量级锁
			}
			
		}.start();
		Thread.sleep(1000);
		//让主线程和子线程产生竞争、抢锁
		synchronized (testLock) {
			System.out.println("------------------------------主线程和子线程发生竞争抢锁-------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//重量级锁
		}
		Thread.sleep(5_000);
		System.out.println("---------------------------------主线程和子线程都释放了锁！----------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());//无锁
		
	}
	
	static class Test{
		
	}
	
}
