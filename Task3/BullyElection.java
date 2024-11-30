import java.io.*;
import java.util.Scanner;
 
class BullyElection {
    static int n;
    static int pro[] = new int[100];
    static int sta[] = new int[100];
    static int co;
     
    public static void main(String args[])throws IOException
    {
        System.out.print("Enter the number of processes: ");
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
         
        int i,j,k,l,m;
         
        for(i=0;i<n;i++)
        {
            System.out.println("For process "+(i+1)+"...");
            System.out.print("Status (1 for alive, 0 for dead): ");
            sta[i]=in.nextInt();
            System.out.print("Process id (1, 2, 3, .., n): ");
            pro[i] = in.nextInt();
        }
         
        System.out.print("Which process will initiate election? ");
        int ele = in.nextInt()-1;
         
        elect(ele);
        System.out.println("Final coordinator is "+co);
    }
     
    // Implement the bully election algorithm
    // The argument is the process id that will initiate the election
    static void elect(int ele) {
        System.out.println("Election initiated by process" + pro[ele]);
        for (int i = ele + 1; i < n; i++) {
            if (sta[i]==1) {
                System.out.println("Process " + pro[ele] + " sends election to Process " + pro[i]);
                elect(i);
            }
        }
        if (pro[ele]>co){
            co = pro[ele];
        }
        System.out.println("Coordinator is Process " + co);
        //TO DO Task: Implement the logic to perform the Bully Algorithm 
          
    }
}