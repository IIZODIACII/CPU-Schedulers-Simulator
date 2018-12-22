package b;

import java.util.Scanner;
import java.util.Vector;

public class OS_3 {

	public static void main(String[] args) {
Scanner In=new Scanner(System.in);
System.out.println("Enter the number of processes");
int n=In.nextInt();
Vector<Process>v=new Vector<Process>();
In.nextLine();
for(int i=0;i<n;i++){
System.out.println("Enter The Name of "+(i+1)+" Process");	
String Name=In.nextLine();
System.out.println("Enter The Burst of "+(i+1)+" Process");
int Burst=In.nextInt();
System.out.println("Enter The Quantum of "+(i+1)+" Process");
int Quantum=In.nextInt();
System.out.println("Enter The Arrival of "+(i+1)+" Process");
int Arrival=In.nextInt();
System.out.println("Enter The Priorty of "+(i+1)+" Process");
int prio=In.nextInt();
In.nextLine();

v.add(new Process(Name, Burst, Quantum, Arrival, prio, i));

}
AG_Schduler AG=new AG_Schduler(v);		
		AG.Schedule();
		
System.out.println(AG.Name);
System.out.println("Waiting Time");
System.out.println(AG.Waiting_Time);
System.out.println("TurnAround Time");
System.out.println(AG.Turnaround_Time);
System.out.println("The Quantum History");
for(int i=0;i<AG.Quantum_History.size();i+=n){
	System.out.print("[");
	for(int j=i;j<n+i;j++)System.out.print(AG.Quantum_History.get(j)+" ");
	System.out.print("]");
	System.out.println("");
}
		In.close();
	}

}
