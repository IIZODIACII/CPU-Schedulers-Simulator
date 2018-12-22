
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        int num, rtq, ct;
        // num -> number of processes, rtq -> round robin time quantum, (ct -> context switch INT FOR NOW!)

        Scanner in = new Scanner(System.in);
        System.out.print("Number Of Processes: ");
        num = in.nextInt();

        System.out.print("Round robin Time Quantum: ");
        rtq = in.nextInt();

        System.out.print("Context Switch ");
        ct = in.nextInt();

        ArrayList<Process> processes = new ArrayList<Process>();
        Vector<Process> v=new Vector<Process>();
        System.out.println();
        for(int i = 0; i < num; i++){
            String name;
            int arrtime, btime, pr;
            System.out.print("Process ("+ (i + 1) +") Name: ");
            in.nextLine();
            name = in.nextLine();


            System.out.print("Process ("+ (i + 1) +") Arrival Time: ");
            arrtime = in.nextInt();

            System.out.print("Process ("+ (i + 1) +") Burst Time: ");
            btime = in.nextInt();

            System.out.print("Process ("+ (i + 1) +") priority: ");
            pr = in.nextInt();

            System.out.println();

            Process obj = new Process(i, name, arrtime, btime, pr);
            v.add(new Process(name, btime, rtq, arrtime, pr, i));
            processes.add(obj);
        }
        in.close();

        Process[] array = processes.toArray(new Process[processes.size()]);

        System.out.println("\nOutput For SJF:\n");
        SJF.findavgTime(array,array.length);
        
        System.out.println("\nOutput For Round Robin:\n");
        RoundRobin.roundRobin(array ,array.length, rtq);


        processes.sort((p1, p2) -> p1.get_ArrTime() - (p2.get_ArrTime())); // Sorting the processes according to ArrivalTime
        System.out.println("\nOutput For Priority Scheduler:\n");

        Priority_Scheduler obj = new Priority_Scheduler(processes, ct);
        obj.Start();


        System.out.println("\nOutput For AG:\n");
        AG_Schduler AG=new AG_Schduler(v);
        AG.Schedule();
        System.out.println(AG.Name);
        System.out.println("Waiting Time");
        System.out.println(AG.Waiting_Time);
        System.out.println("TurnAround Time");
        System.out.println(AG.Turnaround_Time);
        System.out.println("The Quantum History");
        for(int i=0;i<AG.Quantum_History.size();i+=num){
            System.out.print("[");
            for(int j=i;j<num+i;j++)System.out.print(AG.Quantum_History.get(j)+" ");
            System.out.print("]");
            System.out.println("");
        }

    }

    void printArrayList(ArrayList<Process> processes){
        for (Process process : processes)
            System.out.println(process.get_id() + " " + process.get_name() + " " + process.get_ArrTime()
                    + " " + process.get_BTime() + " " + process.get_pr());
    }
}
