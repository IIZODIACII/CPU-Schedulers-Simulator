import java.util.ArrayList;
import java.util.Scanner;

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
            processes.add(obj);
        }





        /*
        for(int i = 0; i < processes.size(); i++)
            System.out.println(processes.get(i).get_id() + " " + processes.get(i).get_name() + " " + processes.get(i).get_ArrTime()
            + " " + processes.get(i).get_BTime() + " " + processes.get(i).get_pr());*/
    }
}
