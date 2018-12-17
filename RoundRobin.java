
public class RoundRobin {
	static void roundRobin (Process[] proc, int n , int q) {
		// total average times 
        int total_wt = 0; 
        int total_tat = 0; 
		
        int[] burst_time = new int[n];
        int[] arrival_time = new int[n];
        
        for ( int i=0 ; i < n ; i++ ) {
        	burst_time[i] =  proc[i].get_BTime(); 
        	arrival_time[i] = proc[i].get_ArrTime();
        }
        int t=0; 
    	int w[] = new int[n]; //waiting time
    	int comp[] = new int[n]; //Completion time
    	
    	while (true) {
    		boolean flag = true;
            for (int i = 0; i < n ; i++) { 
            	// if come before qtime
            	if (arrival_time[i] <= t) { 
            		if (arrival_time[i] <= q) {
            			if (burst_time[i] > 0) {
            				flag = false ;
            				if (burst_time[i] > q) {
            					t = t + q ;
            					burst_time[i] = burst_time[i] - q ;
            					arrival_time[i] = arrival_time[i] + q;
            				}
            				else {
            					// last process 
            					t = t + burst_time[i];
            					comp[i] = t - proc[i].get_ArrTime();
                                w[i] = t - proc[i].get_BTime() - proc[i].get_ArrTime();
                                burst_time[i] = 0;

            				}
            			}
            		}
            		else if (arrival_time[i] > q ) {
            			//check which have less arrival time
            			for (int j = 0; j < n ; j++) {
            				if ( arrival_time[j]  < arrival_time[i]) {
            					if (burst_time[j] > 0) {
                    				flag = false ;
                    				if (burst_time[j] > q) {
                    					t = t + q ;
                    					burst_time[j] = burst_time[j] - q ;
                    					arrival_time[i] = arrival_time[j] + q;
                    				}
                    				else {
                    					// last process 
                    					t = t + burst_time[j];
                    					comp[i] = t - proc[j].get_ArrTime();
                                        w[j] = t - proc[j].get_BTime() - proc[j].get_ArrTime();
                                        burst_time[j] = 0;

                    				}           					
            				}           				
            			}
            		}
            			if (burst_time[i] > 0) {
            				flag = false ;
            				if (burst_time[i] > q) {
            					t = t + q ;
            					burst_time[i] = burst_time[i] - q ;
            					arrival_time[i] = arrival_time[i] + q;
            				}
            				else {
            					// last process 
            					t = t + burst_time[i];
            					comp[i] = t - proc[i].get_ArrTime();
                                w[i] = t - proc[i].get_BTime() - proc[i].get_ArrTime();
                                burst_time[i] = 0;

            				}
            	}
            }

    	}
            	else if (arrival_time[i] > t) {
            		t++; 
                    i--;
            	}
            		}
            if (flag) { 
                break; 
            } 
            	}
        System.out.println("Processes"+"\t\t"+ "Burst time"+"\t\t"+"Waiting time"+"\t\t"+"Turn around time");
        
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + w[i];
            total_tat = total_tat + comp[i];
            System.out.println(proc[i].get_name() + "\t\t\t\t" + proc[i].get_BTime() + "\t\t\t\t " + w[i] + "\t\t\t\t\t " + comp[i]);
        }

        System.out.println("Average waiting time = " +
                (float) total_wt / (float) n);
        System.out.println("Average turn around time = " +
                (float) total_tat / (float) n);

    	
            	
	}
	
	

}
