public class Process {

    private String name;
    private int id;
    private int ArrTime;
    private int BTime;
    private int priority;
    private int waiting;
    private int tat;

    Process(){}

    Process(int i, String n, int At, int Bt, int pr){
        id = i;
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
    public void set_id(int i){
        id = i;
    }
    public void set_waiting(int w){
        waiting = w;
    }
    public void set_tat(int t){
        tat = t;
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
    public int get_id(){
        return id;
    }
    public int get_waiting(){
        return waiting;
    }
    public int get_tat(){
        return tat;
    }

}