package com.qupeng.concurrent.day07.part1;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字的底层原理
 * 
 * -XX:BiasedLockingStartupDelay=0
 * 如果调用wait方法，则立刻变为重量级锁
 * 
 * synchronized内置的锁的膨胀过程不可逆
 * @author qupeng
 */
public class SynchronizedBottomTest05 {

	public static void main(String[] args) throws InterruptedException {
		final Object  testLock=new Object();
		System.out.println("------------------------------加锁之前--------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		synchronized (testLock) {
			System.out.println("-------------------------------加锁之后等待之前------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
			testLock.wait(1_000);
			System.out.println("-------------------------------加锁之后等待之后------------------------------");
			System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
		}
		System.out.println("-------------------------------退出同步代码块之后------------------------------");
		System.out.println(ClassLayout.parseInstance(testLock).toPrintable());
	}

}
