import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

 class Comp implements Comparator<Process> {
    public int compare(Process x, Process y)
    {
        return x.get_pr() - y.get_pr();
    }
}

public class Priority_Scheduler {

    PriorityQueue<Process> processes = new PriorityQueue<Process>(new Comp());
    ArrayList<Process> arr = new ArrayList<Process>(); // an array of processes
    ArrayList<Process> working = new ArrayList<Process>(); // an array of the running processes the last process added in this array is the currently running one
    private int ContextSwitch;
    private int limit = 2; // reduce the priority of a waiting process every "limit" units of time -Aging Technique - to solve the starvation problem
    private boolean add = true; // if the running process finished its work there should be a new process running the "add" indicates that a new process should be running now
    private boolean sw = false; // indicates that there is a process with hiegher priority should run now instead of the current one -the lower the number the heigher the priority-.

    Priority_Scheduler(){}
    Priority_Scheduler(ArrayList prs, int t) {
        ContextSwitch = t;
        arr = prs;
        for (int i = 0; i < arr.size(); i++) {
            processes.add(arr.get(i));
        }
    }

    public void Start(){
        Process nxt = arr.get(0); // the next process that should be running
        int time = 0;
        int idx = 0; // the index of the currently running process

        while (processes.size() > 0) {
            if (sw) { // if there is aa process that should switch and run instead of the currently running process
                if (nxt.get_ArrTime() > time) // if the new process didn't arrive yet
                    time += ContextSwitch + (nxt.get_ArrTime() - time); // add the ContextSwitch and add the remaining time untill the new process arrives
                else {
                    if(ContextSwitch != 0)
                        time += ContextSwitch;
                    else
                        time++;
                }
            }
            else
                time++;

            if (add || sw) { // if there is a switch or just a new process should start running now
                working.add(nxt);
                nxt.set_rt(time);
                sw = false; // set false so the process won't add itself again in the next iteration
                add = false; // set false so the process won't add itself again in the next iteration
            }

            for (Process p : processes) { // Loop through the processes
                if (p != nxt && p.get_ArrTime() <= time && p.get_pr() > 1 && p.get_rt() > 1){ // if the p is not the current running process && the pr. arrived && the priority wasn't reduced before
                    if (time - p.get_rt() >= limit) // if it passed limit unit of time and the process's priority wasn't reduced
                        p.set_pr(p.get_pr() - 1);
                }
                else if(p != nxt && p.get_ArrTime() <= time && p.get_pr() > 1 && time - p.get_ArrTime() >= limit) { // if the p is not the current running process && the pr. arrived
                    p.set_pr(p.get_pr() - 1); // reduce the priority
                    p.set_rt(time); // the priority was reduced at this time so it will not reduce again in the next iteration, it will wait "limit" unit of time to be reduced again
                }
            }


            Process n = Get_Nxt(nxt, time); // check for switch
            if (n != nxt) { // if the n is not the currently running process then there should be a switch
                idx++; // point to the new process and add it in the next iteration
                nxt = n;

                if (sw) {
                    int t = idx - 1; // get the index of the currently running process
                    Process temp = working.get(t); // get the currently running process

                    if (temp.get_BTime() > 0) // if it didn't finish reduce its BurstTime
                        temp.set_BTime(temp.get_BTime() - 1);

                    if (temp.get_BTime() == 0) {// if it is done calculate the required values
                        temp.set_ct(time);
                        temp.set_tat(temp.get_ct() - temp.get_ArrTime());
                        temp.set_waiting(temp.get_tat() - temp.get_CBTime());

                        processes.removeIf(Process -> temp.get_id() == Process.get_id()); // remove the process from the queue -the process may not be at the top of the queue-
                    }

                    working.set(t, temp); // replace the currently working process with the modified copy
                }
            }
            else { // if there is no switch
                int t = idx;
                Process temp = working.get(t);

                if (temp.get_BTime() > 0)
                    temp.set_BTime(temp.get_BTime() - 1);

                if (temp.get_BTime() == 0) {
                    temp.set_ct(time);
                    temp.set_tat(temp.get_ct() - temp.get_ArrTime());
                    temp.set_waiting(temp.get_tat() - temp.get_CBTime());

                    processes.poll(); // remove the process from the queue -the process is always at the top of the queue-

                    Process ne = Get_Nxt(nxt, time); // get a new process to run it
                    if (nxt != ne) {
                        idx++;
                        nxt = ne;
                    }
                }
                working.set(t, temp);
            }
        }

        print();
    }

    private Process Get_Nxt(Process nxt, int time){
        Process same = nxt; // checks if nxt hasn't changed

        for(Process pr : processes) {
            if (pr.get_ArrTime() <= time && pr.get_BTime() > 0) { // if the process has arrived and is hasn't finish its work
                if(nxt.get_BTime() == 0) { // if the currently running process has finished its work get a new one
                    nxt = pr;
                    add = true;
                    sw = false;
                    break;
                }
                else if (pr.get_pr() <= nxt.get_pr() && pr != nxt){ // if the currently running process priority is lower than another process
                    nxt = pr;
                    sw = true;
                    add = false;
                    break;
                }
            }
        }
        if (same == nxt){ // if nxt hasn't changed then there is no switch and there is no new process to run
            add = false;
            sw = false;
        }
        return nxt;
    }


    private void print(){
        System.out.println("Processes Execution Order:");
        for (int i = 0; i < working.size(); i++)
            System.out.print(working.get(i).get_name()+ " ");
        System.out.println();
        System.out.println();

        double wt_avg = 0;
        double tat_avg = 0;
        for (int i = 0; i < arr.size(); i++){
            System.out.println("Process ("+arr.get(i).get_name() + ") Details:\nWaiting Time: " +arr.get(i).get_waiting()+
                    "\nTurnAroundTime: " +arr.get(i).get_tat());
            System.out.println();
            wt_avg += arr.get(i).get_waiting();
            tat_avg = arr.get(i).get_tat();
        }

        System.out.println("Average Waiting Time: "+ (wt_avg / arr.size()));
        System.out.println("Average TurnAroundTime: "+ (tat_avg / arr.size()));
    }
 }





