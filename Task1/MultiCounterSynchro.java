
public class MultiCounterSynchro extends Thread {

   private static int counter = 0;
   private static final int limit = 100;

   public static void main(String argv[]) throws InterruptedException {
      MultiCounterSynchro t1 = new MultiCounterSynchro();
      MultiCounterSynchro t2 = new MultiCounterSynchro();
      MultiCounterSynchro t3 = new MultiCounterSynchro();

      t1.start();
      t2.start();
      t3.start();

      t1.join();
      t2.join();
      t3.join();
   }

   public void run() {
      while (counter < limit) {
          incrementCounter();
      }
   }
   

   public synchronized void incrementCounter() {
         System.out.println(Thread.currentThread().getName() + " counter : " + counter);
         counter++;
   }

}
