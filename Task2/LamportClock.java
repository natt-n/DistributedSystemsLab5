
import java.util.*;

public class LamportClock {
   int c;
   String state;
   Queue<Request> requestQueue;
   int processID;
   int numProcesses;
   Set<Integer> replies;

   // Each process keeps an integer initially 0
   public LamportClock(int processID, int numProcesses) {
      this.c = 0;
      this.state = "RELEASED";
      this.requestQueue = new LinkedList<>();
      this.processID = processID;
      this.numProcesses = numProcesses;
      this.replies = new HashSet<>();
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter Number of processes: ");
      int totalProcesses = Integer.parseInt(scanner.nextLine());

      System.out.println("Enter your process id: ");
      int processID = Integer.parseInt(scanner.nextLine());

      LamportClock process = new LamportClock(processID, totalProcesses);

      while (true) { 
         System.out.println("\n1. Request Access\n2. Recieve Request\n3. Exit");
         int choice = Integer.parseInt(scanner.nextLine());
         if (choice==1) {
            process.requestAccess(scanner);
         } 
         else if (choice == 2) {
            System.out.println("Enter process id  and timestamp (space seperated): ");
            String[] input = scanner.nextLine().split(" ");
            int srcID = Integer.parseInt(input[0]);
            int timestamp = Integer.parseInt(input[1]);
            process.recieveRequest(srcID, timestamp);
         }
         else if (choice == 3) { 
            System.out.println("Exiting...");
            break;

         }

      }
      scanner.close();
   }

   public int getValue() {
      return c;
   }

   // Internal even or local step  
   public void localStep() {
      c = c + 1;
   }

   public void sendEvent() {
      c = c + 1;
   }

   public void receiveEvent(int src, int sentValue) {
      c = max(c, sentValue) + 1;
   }
    
   public int max(int a, int b) {
      return a > b ? a : b;
   }

   public synchronized void requestAccess(Scanner scanner){
      localStep();
      state = "WANTED";
      replies.clear();

      System.out.println("Process " + processID + " is requesting critical section ");
      for(int i = 0; i < numProcesses; i++) {
         if (i != processID) {
            System.out.println("Enter reply (Y/N) from Process " + i + ": ");
            String reponse = scanner.nextLine().trim();
            if (reponse.equals("Y")){
               replies.add(i);
            }
         }
      }

      if (replies.size() == numProcesses - 1) {
         state = "HELD";
         System.out.println("Process " + processID + " enter critical section");
         try {
             Thread.sleep(2000);
         } catch (Exception e) {
            e.printStackTrace();
         }
         releaseAccess();
      }

   }

   public synchronized void releaseAccess() {
      state = "RELEASED"; 
      System.out.println("Process " + processID + " releases critical section");
      while (!requestQueue.isEmpty()) {
         Request req = requestQueue.poll();
         sendReply(req.processID);
      }
   }

   public synchronized void recieveRequest(int srcProcessID, int timestamp) {
      receiveEvent(srcProcessID, timestamp);
      Request currentRequest = new Request(processID, c);
      Request incomingRequest = new Request(srcProcessID, timestamp);

      if (!state.equals("HELD")&& (state.equals("RELEASED")||compareRequests(incomingRequest, currentRequest) < 0)) {
         sendReply(srcProcessID);

      }
      else {
         requestQueue.add(incomingRequest);
      }
   }

   public void sendReply(int targetProcessID) {
      System.out.println("Process " + processID + " sends reply to Process "+ targetProcessID);

   }

   private int compareRequests(Request r1, Request r2) {
      if (r1.timestamp != r2.timestamp) {

         return Integer.compare(r1.timestamp, r2.timestamp);
      }
      return Integer.compare(r1.processID, r2.processID);

   }

   static class Request {
      int processID;
      int timestamp;

      public Request(int processID, int timestamp){
         this.processID = processID;
         this.timestamp = timestamp;
      }
   }

   

}
