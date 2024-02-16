import java.util.*;

public class Ex4_20220808056 {
}
class CPU{
    private String name;
    private double clock;
    CPU(String name,double clock){
        this.clock=clock;
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public double getClock(){
        return clock;
    }
    public int compute(int a,int b){
        return a+b;
    }
    public String toString(){
        return "CPU:"+name+" "+clock+"Ghz";
    }
}
class RAM{
    private String type;
    private int capacity;
    private int[][] memory;
    public RAM(String type,int capacity){
        this.capacity=capacity;
        this.type=type;
    }
    public int getCapacity(){return capacity;}
    public String getType(){return type;}


//?
    private void initMemory(int capacity){
        Random random=new Random();
        memory=new int[capacity][capacity];
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < capacity; j++) {
                memory[i][j] = random.nextInt(11);
        }
    }}
    private boolean check(int i, int j){
        if (i<0||j<0||i>memory.length||j>memory.length) return false;
        return true;
    }
    public int getValue(int i,int j){
        if (!check(i,j)) return -1;
        return memory[i][j];
    }

    public void setValue(int i,int j,int value){
        if (check(i,j)){
        memory[i][j]=value;
        }
    }
    public String toString(){
        return "RAM: " + type+ " " + capacity+ "GB";
    }
}
class Computer{
   protected CPU cpu;
   protected RAM ram;
   Computer (CPU cpu,RAM ram){
       this.cpu=cpu;
       this.ram=ram;
   }
   public void run(){
       int sum=0;
       for (int i=0;i< ram.getCapacity();i++){
           int value= ram.getValue(i,i);
           if (value!=-1){
               sum=cpu.compute(sum,value);
           }
       }
       ram.setValue(0,0,sum);
   }
   public String toString(){
       return  "Computer: " + cpu + " " + ram;
   }
}
class Laptop extends Computer{
    private int milliAmp;
    private int battery;
    Laptop(CPU cpu,RAM ram,int milliAmp){
        super(cpu,ram);
        this.milliAmp=milliAmp;
        battery=milliAmp*30/100;
    }
    public int batteryPercentage(){
        return battery*100/milliAmp;
    }
  //  public void charge(){
       // for (battery=battery;battery<91;battery+=2){}
        public void charge(){
            while(battery < milliAmp * 90 /100){
                battery += milliAmp * 2 / 100;
                if(battery > milliAmp * 90 / 100)
                    battery = milliAmp * 90 / 100;
            }
        }

    @Override
    public void run(){
        if (batteryPercentage()>5){
            super.run();
           // for (battery=battery;battery<1;battery-=3){}
            battery -= milliAmp * 3 / 100;//decrease battery %3 if it is > %5
            if(battery < 0) battery = 0;
        }
        else charge();
    }
    public String toString(){
        return super.toString() +" " +battery;
    }
}
class Desktop extends Computer{
    private ArrayList<String> peripherals;
    Desktop(CPU cpu,RAM ram,String... peripherals){
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
}
    @Override
    public void run() {
        super.run();
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            int value = ram.getValue(i, i);
            if (value != -1) {
                sum = cpu.compute(sum, value);
            }
        }
        ram.setValue(0, 0, sum);
    }

    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }

    public void plugOut(int index) {
        peripherals.remove(index);
    }
    @Override
    public String toString() {
        return super.toString() + " " + String.join(" ", peripherals);
    }
}
