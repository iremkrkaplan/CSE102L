import java.util.Scanner;
import java.util.Random;
import static java.lang.System.out;

public class Ex1_20220808056 {

    public static void main(String[] args) {
        Stock stock = new Stock("ORCL", "Oracle Corporation");
        stock.previousClosingPrice = 34.5;
        Stock.currentPrice = 34.35;
        double changePercent = stock.getChangePercent();
        System.out.println("Fiyat değisim yüzdesi: " + changePercent + "%");

        //takes an array of Fans
        Scanner input=new Scanner(System.in);
        System.out.print("Fan sayısını giriniz: ");
        int numOfFans=input.nextInt();
        Fan[] fans=new Fan[numOfFans];
        for(int i=0;i<numOfFans;i++){
            boolean isOn = Math.random() < 0.5;
            if (i%2==0){

                //tek
                fans[i]=new Fan(i+1,"yellow");
                fans[i].setOn(isOn);
            }
            //çift default olacak
            else {
                fans[i] = new Fan();
            }
                System.out.println("Fan" + (i + 1) + ":" + fans[i].toString());

        }



        everyThirdFan(fans);
        out.println("hızlandırdıktan sonra: ");
        for (int i=0;i<numOfFans;i++){
            System.out.println("Fan"+(i+1)+":"+fans[i].toString());
        }

    }
    public static void everyThirdFan(Fan[] fans){
        for (int i=2;i<fans.length;i+=3){
            Fan fan=fans[i];
            if (fan.isOn()) {
                fan.increaseSpeed();
            System.out.println("Fan" + (i+1) + " hızlandı!");}
        }


    }

    public static class Stock {
        Stock() {
        }
        public String symbol;
        public String name;
        public double previousClosingPrice;
        public static double currentPrice;

        public Stock(String symbol, String name) {
            this.symbol = symbol;
            this.name = name;
        }
        public double getChangePercent() {
            return (currentPrice-previousClosingPrice)/previousClosingPrice*100.0;
        }

    }

    public static class Fan{

        int SLOW=1;
        int MEDIUM=2;
        int FAST=3;
        private int speed;
        private boolean on;
        private double radius;
        private String color;
        //A no-arg constructor that created a default fan
        //varsayılan değerlerle Ex1_20220808056.Fan nesnesi oluşturmak için kullanılır
        public Fan(){
            this.speed=SLOW;
            this.on=false;
            this.color="blue";
            this.radius=5;
        }

        //A constructor that takes radius and color as parameters and creates a fan

        //"radius" ve "color" değişkenleri, parametrelerde verilen değerlere eşitlenir
        // ve fan nesnesi bu değerlerle oluşturulur. Diğer üç veri alanı
        // (hız, açık/kapalı durum),
        // yine varsayılan değerlerle (SLOW ve false) başlatılır

        //belirli bir renk ve yarıçap değerleri ile Ex1_20220808056.Fan nesnesi oluşturmak
        // için kullanılabilir
        public Fan(double radius, String color) {
            this.speed = SLOW;
            this.on = false;
            this.radius = radius;
            this.color = color;
        }

        //The accessor and mutator methods for all four data fields
        //mutators (değiştirici)
        public void setSpeed(int speed){
            this.speed=speed;
        }
        public void setOn(boolean on) {
            this.on = on;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public void setColor(String color) {
            this.color = color;
        }
        //accessors (erişimci)

        public int getSpeed() {
           this.speed = (int) (Math.random() * 3) + 1;
            return this.speed;

        }
        public boolean isOn() {
          //  boolean isOn = Math.random() < 0.5;
            return this.on;
        }
        ////////////////////

        public double getRadius() {
            return this.radius;
        }

        public String getColor() {
            return this.color;
        }
        //for the testfan
        public void increaseSpeed(){
            if (this.speed==FAST) this.speed=SLOW;
            else {
                this.speed++;
            }
        }
        public String toString() {
            if (this.on) {
                return "Fan is on. "+"Speed: " + this.speed + ", Color: " + this.color + ", Radius: " + this.radius;
            } else {
                return  "Fan is off. "+"Color: " + this.color + ", Radius: " + this.radius;
            }
        }}
}
