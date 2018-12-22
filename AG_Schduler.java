

import java.util.TreeMap;
import java.util.Vector;

public class AG_Schduler {
Vector<String>Name=new Vector<String>();	
Vector<Integer>Waiting_Time=new Vector<Integer>();
Vector<Integer>Turnaround_Time=new Vector<Integer>();
Vector<Integer>Quantum_History=new Vector<Integer>();	
int num_of_processes;
int run_time;
boolean Finished;
boolean Processing;
TreeMap<Integer,Vector<Process> >Q1=new TreeMap<Integer, Vector<Process>>();
TreeMap<Integer,Vector<Process> >Q2=new TreeMap<Integer, Vector<Process>>();
TreeMap<Integer,Vector<Process> >Q3=new TreeMap<Integer, Vector<Process>>();

public AG_Schduler(Vector<Process>v){
this.Processing=false;	
this.Finished=false;
this.num_of_processes=v.size();
this.Name.setSize(num_of_processes);
this.Waiting_Time.setSize(num_of_processes);	
this.Turnaround_Time.setSize(num_of_processes);
this.Quantum_History.setSize(num_of_processes);
for(int i=0;i<num_of_processes;i++){
this.Name.setElementAt(v.get(i).Name, v.get(i).Index);
this.Waiting_Time.setElementAt(0, i);
this.Turnaround_Time.setElementAt(0, i);
this.Quantum_History.setElementAt(v.get(i).Quantum, v.get(i).Index);
Vector<Process>s=new Vector<Process>();
s.add(v.get(i));
if(Q1.containsKey(v.get(i).Arrival))Q1.get(v.get(i).Arrival).add(v.get(i));
else Q1.put(v.get(i).Arrival,s);

}
	



    }	
public void Schedule(){
int n=num_of_processes;
	while(n>0){
int first_arrival=-1;
if(Q1.size()>0)first_arrival=Q1.firstKey();
for(int i=0;i<num_of_processes;i++)Quantum_History.add(-1);		
if(first_arrival<=run_time&&first_arrival!=-1)E1();		
else if(Q2.size()>0)E2();		
else if(Q3.size()>0) E3();		
if(!Processing&&!Finished){
int indx=Q1.firstEntry().getValue().firstElement().Index;	
Waiting_Time.setElementAt(Waiting_Time.get(indx)+(Q1.firstKey()-run_time), indx);
Turnaround_Time.setElementAt(Turnaround_Time.get(indx)+(Q1.firstKey()-run_time), indx);
run_time=Q1.firstKey();}
n-=(Finished?1:0);		
Finished=Processing=false;
for(int i=Quantum_History.size()-num_of_processes;i<Quantum_History.size();i++)
	if(Quantum_History.get(i)==-1)Quantum_History.setElementAt(Quantum_History.get(i-num_of_processes), i);
	
	}
	
	
	
}	
public void E1(){
Process P=Q1.firstEntry().getValue().remove(0);	
if(Q1.firstEntry().getValue().size()==0)Q1.remove(Q1.firstKey());	
int Waiting=run_time-P.Arrival;	
int service=Math.min(P.Burst,(int)Math.ceil(P.Quantum*0.25));	
P.Arrival+=service;
P.Burst-=service;
Waiting_Time.setElementAt(Waiting_Time.get(P.Index)+Waiting, P.Index);
Turnaround_Time.setElementAt(Turnaround_Time.get(P.Index)+Waiting+service, P.Index);	
run_time+=service;
if(P.Burst==0){P.Quantum=0;Finished=true;}
else {
P.Quantum+=2;
Vector<Process>s=new Vector<Process>();
s.add(P);
if(Q2.containsKey(P.Priorty))Q2.get(P.Priorty).add(P);
else Q2.put(P.Priorty, s);
Processing=true;
}	
Quantum_History.setElementAt(P.Quantum,(Quantum_History.size()-num_of_processes)+P.Index);


}

public void E2(){
	Process P=Q2.firstEntry().getValue().remove(0);	
	if(Q2.firstEntry().getValue().size()==0)Q2.remove(Q2.firstKey());	
	int Waiting=run_time-P.Arrival;	
	int service=Math.min(
	Math.min(P.Burst,Q1.size()>0? Q1.firstKey()-run_time:P.Burst),(int)Math.ceil(P.Quantum*0.25));	
	P.Arrival+=service;
	P.Burst-=service;
	Waiting_Time.setElementAt(Waiting_Time.get(P.Index)+Waiting, P.Index);
	Turnaround_Time.setElementAt(Turnaround_Time.get(P.Index)+Waiting+service, P.Index);	
	run_time+=service;
		
	if(P.Burst==0){Finished=true;P.Quantum=0;}
	else{
	P.Quantum+=((int)Math.ceil(P.Quantum*0.5));
	Vector<Process>s=new Vector<Process>();
	s.add(P);
	if(Q3.containsKey(P.Burst))Q3.get(P.Burst).add(P);
	else Q3.put(P.Burst, s);
	Processing=true;	
	}
Quantum_History.setElementAt(P.Quantum,(Quantum_History.size()-num_of_processes)+P.Index);	
	
	}

public void E3(){
	Process P=Q3.firstEntry().getValue().remove(0);
	if(Q3.firstEntry().getValue().size()==0)Q3.remove(Q3.firstKey());	
	int Waiting=run_time-P.Arrival;	
	int service=Math.min(P.Burst,Q1.size()>0? Q1.firstKey()-run_time:P.Burst);	
	P.Arrival+=service;
	P.Burst-=service;
	Waiting_Time.setElementAt(Waiting_Time.get(P.Index)+Waiting, P.Index);
	Turnaround_Time.setElementAt(Turnaround_Time.get(P.Index)+Waiting+service, P.Index);	
	run_time+=service;
	
	if(P.Burst==0){Finished=true;P.Quantum=0;}
	else{
	P.Quantum+=(service%P.Quantum);
	Vector<Process>s=new Vector<Process>();
	s.add(P);
	if(Q3.containsKey(P.Burst))Q3.get(P.Burst).add(P);
	else Q3.put(P.Burst, s);
	Processing=true;
	}
Quantum_History.setElementAt(P.Quantum,(Quantum_History.size()-num_of_processes)+P.Index);	
	
	
	
}







}
