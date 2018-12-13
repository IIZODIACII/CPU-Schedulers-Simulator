public class Process {

    private String name;
    private int ArrTime;
    private int BTime;
    private int priority;


    Process(){}

    Process(String n, int At, int Bt, int pr){
        name = n;
        ArrTime = At;
        BTime = Bt;
        priority = pr;
    }

    public void set_name(String n){
        name = n;
    }
    public void set_ArrTime(int t){
        ArrTime = t;
    }
    public void set_BTime(int t){
        BTime = t;
    }
    public void set_pr(int p) {
        priority = p;
    }
    public String get_name(){
        return name;
    }
    public int get_ArrTime(){
        return ArrTime;
    }
    public int get_BTime(){
        return BTime;
    }
    public int get_pr(){
        return priority;
    }

}