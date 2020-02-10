package com.qupeng.concurrent.day07.part1;



/**
 * synchronized关键字的底层原理
 * 
 * 偏向锁和轻量级锁对比
 * -XX:BiasedLockingStartupDelay=0
 *
 *
 * 无锁:74ms
 * 偏向锁:1171ms
 * 轻量级锁:9202ms
 * 重量级锁:52881ms
 *
 * @author qupeng
 */
public class SynchronizedBottomTest07 {
	
	public static void main(String[] args) throws InterruptedException {
		Test test=new Test();
		
		long beforeTime = System.currentTimeMillis();
		//执行10亿次
		for(int i=0;i<1000000000;i++){
			test.testIncreament();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时是："+(endTime-beforeTime)+"毫秒");
	}
	
	static  class  Test{
		
		private int l=0;
		
		public synchronized void testIncreament(){
			l++;
		}
		
	}
	
		

}
