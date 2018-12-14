public class SJF {

    static void findWaitingTime(Process[] proc, int n, int[] wt) {
        int[] burst_time = new int[n];

        for (int i = 0; i < n; i++)
            burst_time[i] = proc[i].get_BTime();

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        //Start processes
        System.out.println("Process Execution Order");
        while (complete != n) {

            //find the minimum burst time
            for (int j = 0; j < n; j++) {
                // checks if the current process is the smallest burst time
                if ((proc[j].get_ArrTime() <= t) && (burst_time[j] < minm) && burst_time[j] > 0) {
                    minm = burst_time[j];
                    shortest = j;
                    check = true;
                }
            }

            // if the current process is the smallest process
            if (!check) {
                t++;
                continue;
            }

            // Reduce remaining time by one
            burst_time[shortest]--;

            // Update minimum
            minm = burst_time[shortest];


            // If a process gets completely executed
            if (burst_time[shortest] == 0) {
                //updates minumem with the biggest integer for the next run
                minm = Integer.MAX_VALUE;

                complete++;
                check = false;
                finish_time = t + 1;

                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].get_BTime() - proc[shortest].get_ArrTime();

                if (wt[shortest] < 0) {
                    wt[shortest] = 0;
                }
            }
            t++;
            System.out.print("P" + proc[shortest].get_name() +" ");
        }
        System.out.println();
    }

    static void findTurnAroundTime(Process[] proc, int n,
                                   int[] wt, int[] tat) {
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].get_BTime() + wt[i];
    }

    static void findavgTime(Process[] proc, int n) {
        int[] wt = new int[n];
        int[] tat = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(proc, n, wt);

        findTurnAroundTime(proc, n, wt, tat);

        System.out.println("Processes"+"\t\t"+ "Burst time"+"\t\t"+"Waiting time"+"\t\t"+"Turn around time");
        
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            System.out.println(proc[i].get_name() + "\t\t\t\t" + proc[i].get_BTime() + "\t\t\t\t " + wt[i] + "\t\t\t\t\t " + tat[i]);
        }

        System.out.println("Average waiting time = " +
                (float) total_wt / (float) n);
        System.out.println("Average turn around time = " +
                (float) total_tat / (float) n);


    }
}

