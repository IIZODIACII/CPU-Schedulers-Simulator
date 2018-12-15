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
    ArrayList<Process> arr = new ArrayList<Process>();
    ArrayList<Process> working = new ArrayList<Process>();
    private int ContextSwitch;
    private int limit = 2; // reduce the priority of a waiting process every 10 units of time
    private boolean add = true;
    private boolean sw = false;
    Priority_Scheduler(){}

    Priority_Scheduler(ArrayList prs, int t) {
        ContextSwitch = t;
        arr = prs;
        for (int i = 0; i < arr.size(); i++) {
            processes.add(arr.get(i));
        }
    }

    public void Start(){
        Process nxt = arr.get(0);
        int time = 0;
        int idx = 0;

        while (processes.size() > 0) {
            if (sw) {
                if (nxt.get_ArrTime() > time)
                    time += ContextSwitch + (nxt.get_ArrTime() - time);
                else {
                    if(ContextSwitch != 0)
                        time += ContextSwitch;
                    else
                        time++;
                }
            }
            else
                time++;

            if (add || sw) {
                working.add(nxt);
                nxt.set_rt(time);
                sw = false;
                add = false;
            }

            for (Process p : processes) {
                if (p != nxt && p.get_ArrTime() <= time && p.get_pr() > 1 && p.get_rt() > 1){
                    if (time - p.get_rt() >= limit)
                        p.set_pr(p.get_pr() - 1);
                }
                else if(p != nxt && p.get_ArrTime() <= time && p.get_pr() > 1 && time - p.get_ArrTime() >= limit) {
                    p.set_pr(p.get_pr() - 1);
                    p.set_rt(time);
                }
            }


            Process n = Get_Nxt(nxt, idx, time); // check for switch
            if (n != nxt) {
                idx++;
                nxt = n;
                if (sw) {
                    int t = idx - 1;
                    Process temp = working.get(t);

                    if (temp.get_BTime() > 0)
                        temp.set_BTime(temp.get_BTime() - 1);

                    if (temp.get_BTime() == 0) {
                        temp.set_ct(time);
                        temp.set_tat(temp.get_ct() - temp.get_ArrTime());
                        temp.set_waiting(temp.get_tat() - temp.get_CBTime());

                        processes.removeIf(Process -> temp.get_id() == Process.get_id());
                    }

                    working.set(t, temp);
                }
            }
            else {
                int t = idx;
                Process temp = working.get(t);

                if (temp.get_BTime() > 0)
                    temp.set_BTime(temp.get_BTime() - 1);

                if (temp.get_BTime() == 0) {
                    temp.set_ct(time);
                    temp.set_tat(temp.get_ct() - temp.get_ArrTime());
                    temp.set_waiting(temp.get_tat() - temp.get_CBTime());

                    processes.poll();

                    Process ne = Get_Nxt(nxt, idx, time); // check for new process
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

    private Process Get_Nxt(Process nxt, int idx, int time){
        Process same = nxt;
        if (processes.size() == 1)
            return processes.peek();
        for(Process pr : processes) {
            if (pr.get_ArrTime() <= time && pr.get_BTime() > 0) {
                if(nxt.get_BTime() == 0) {
                    nxt = pr;
                    add = true;
                    sw = false;
                    break;
                }
                else if (pr.get_pr() <= nxt.get_pr() && pr != nxt){
                    nxt = pr;
                    sw = true;
                    add = false;
                    break;
                }

            }
        }
        if (same == nxt){
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





