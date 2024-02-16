public class Ex8_20220808056 {
}
interface Sellable{
    String getName();
    double getPrice();
}
interface Wrappable extends Sellable{

}
interface Package<T>{
    boolean pack(T item);
    T extract();
    boolean isEmpty();

}
abstract class Product implements Sellable{
    private String name;
    private double price;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString(){
        return getClass().getSimpleName()+"("+name+","+price+")";
    }
}
 class Mirror extends Product{
    private int width;
    private int height;

    public Mirror(String name, double price, int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }
    public int getArea(){
        return width*height;
    }
   public <T> T reflect(T item){
       System.out.println("Reflecting item: " + item);
       return item;
    }


 }
 class Paper extends Product implements Wrappable{
    private String note;

    public Paper(String name, double price, String note) {
        super("A4", 3);
        this.note = note;
    }

    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}

 }
 class Matroschka<T extends Wrappable> extends Product implements Wrappable,Package{
    private T item;

    public Matroschka(String name, double price, T item) {
        super("Doll", 5);
        this.item = item;
    }
    @Override
    public String toString(){
        return super.toString()+"{"+item+"}";
    }

     @Override
     public boolean pack(Object itemToPack) {
         if (isEmpty()) {
             item = (T) itemToPack;
             return true;
         }
         return false;
     }
     @Override
     public T extract() {
         if (!isEmpty()) {
             T extractedItem = item;
             item = null;
             return extractedItem;
         }
         return null;
     }
     @Override
     public boolean isEmpty() {
         return item == null;
     }
 }
abstract class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;

    public Box() {
        this.item=null;
        this.seal=false;
    }

    public Box(T item) {
        this.item = item;
        this.seal=true;
    }
   @Override
   public T extract() {
       if (!isEmpty()) {
           T extractedItem = item;
           item = null;
           return extractedItem;
       }
       return null;
   }
    @Override
    public boolean isEmpty() {
        return item == null;
    }
    @Override
    public String toString(){
        return getClass().getSimpleName()+"{"+item+"}Seal:"+seal;
    }
}