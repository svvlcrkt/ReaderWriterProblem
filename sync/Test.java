package sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Test {
	public static void main(String [] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ReadWriteLock RW = new ReadWriteLock();
		
		
		executorService.execute(new Writer(RW));
		executorService.execute(new Writer(RW));
		executorService.execute(new Writer(RW));
		executorService.execute(new Writer(RW));
		
		executorService.execute(new Reader(RW));
		executorService.execute(new Reader(RW));
		executorService.execute(new Reader(RW));
		executorService.execute(new Reader(RW));   
        
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } 
        catch (InterruptedException exception) {}
        RW.stopThreads();
        executorService.shutdown();
	}
}


class ReadWriteLock{

    private Semaphore readS = new Semaphore(1);
    private Semaphore writeS = new Semaphore(1);
    
    private int rCount = 0;

    private final AtomicBoolean k_work = new AtomicBoolean(true);

    public void readLock() throws InterruptedException {
        readS.acquire();
        rCount++;
        if(rCount == 1){
            writeS.acquire();
        }
        readS.release();

		System.out.println("Thread "+Thread.currentThread().getName() + " is reading now");
        Thread.sleep(1500);
        System.out.println("Thread "+Thread.currentThread().getName() + " has finished reading");		

    }

    public void readUnlock() throws InterruptedException {
        readS.acquire();
        rCount--;
        if(rCount == 0){
            writeS.release();
        }
        readS.release();
    }

   
    public void writeLock() throws InterruptedException {
        writeS.acquire();
		System.out.println("Thread "+Thread.currentThread().getName() + " is writing now");
        Thread.sleep(2000);		
        System.out.println("Thread "+Thread.currentThread().getName() + " has finished writing");
    }

   
    public void writeUnlock() throws InterruptedException{
        writeS.release();
    }

    public void stopThreads(){
        k_work.set(false);
    }

    public boolean kWork(){
        return k_work.get();
    }
}

class Writer implements Runnable
{
    private ReadWriteLock RW_lock;

    public Writer(ReadWriteLock rw) {
    	RW_lock = rw;
   }
    public void run() {
      while (RW_lock.kWork()){
    	  try {
			RW_lock.writeLock();
			RW_lock.writeUnlock();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		} 
      }
   }
}

class Reader implements Runnable
{
   private ReadWriteLock RW_lock;
   
    public Reader(ReadWriteLock rw) {
    	RW_lock = rw;
   }
    public void run() {
      while (RW_lock.kWork()){ 			  
		try {
			RW_lock.readLock();
			RW_lock.readUnlock();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
      }
   }

}
