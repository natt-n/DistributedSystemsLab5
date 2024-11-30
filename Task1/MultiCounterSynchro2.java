
public class MultiCounterSynchro2 extends Thread {

   private static int counter = 0;
   private static final int limit = 100;

   public static void main(String argv[]) throws InterruptedException {
      MultiCounterSynchro2 t1 = new MultiCounterSynchro2();
      MultiCounterSynchro2 t2 = new MultiCounterSynchro2();
      MultiCounterSynchro2 t3 = new MultiCounterSynchro2();

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
   

   public void incrementCounter() {
      synchronized (this) {
         System.out.println(Thread.currentThread().getName() + " counter : " + counter);
         counter++;
      }
   }
}
