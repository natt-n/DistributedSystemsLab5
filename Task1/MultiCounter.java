
public class MultiCounter extends Thread {

   private static int counter = 0;
   private static final int limit = 100;

   public static void main(String argv[]) throws InterruptedException {
      MultiCounter t1 = new MultiCounter();
      MultiCounter t2 = new MultiCounter();
      MultiCounter t3 = new MultiCounter();

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
      System.out.println(Thread.currentThread().getName() + " counter : " + counter);
      counter++;
   }

}
