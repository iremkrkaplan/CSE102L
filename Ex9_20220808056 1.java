import java.util.*;
import java.lang.*;

public class Ex9_20220808056 {
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
    double getPriority();

}
interface Common<T>{
    Boolean isEmpty();
    T peek();
    int size();
}
interface Stack<T>{
    boolean push(T item);
    T pop();
}
interface Node<T>{
    int DEFAULT_CAPACITY = 2;
    void setNext(T item);
    T getNext();
    double getPriority();
}
interface PriorityQueue <T> extends Common <T>{
    int FLEET_CAPACITY = 3;
    Boolean enqueue(T item);
    T dequeue();
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

    public Mirror(int width, int height) {
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
 class Matroschka<T extends Wrappable> extends Product implements Wrappable,Package {
    private T item;

    public Matroschka(T item) {
        super("Doll", item.getPrice()+5);
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

     @Override
     public double getPriority() {
         if (this instanceof Matroschka) {
             throw new UnsupportedOperationException("Not implemented");
         } else {
             return 0;
         }
     }

 }
abstract class Box<T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;
    private int distanceToAddress;


    public Box() {
        this.item=null;
        this.seal=false;

    }

    public Box(T item,int distanceToAddress) {
        this.item = item;
        this.distanceToAddress=distanceToAddress;
        this.seal=true;
    }

    public T getContent() {
        return item;
    }

    @Override
    public boolean pack(T item) {
        if (item instanceof Box) {
            return false;
        }
        return true;
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

    public int getDistanceToAddress() {
        return distanceToAddress;
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
//public abstract class Box<T extends Sellable> implements Package<T>, Comparable<Box<T>> {
class Container implements Stack<Box<?>>,Node,Comparable<Container>{

    private Box <?> [] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;


    public Container() {
        this.boxes = new Box[DEFAULT_CAPACITY];
        this.top = -1;
        this.size = 0;
        this.priority = 0;
        this.next = null;
    }
    @Override
    public String toString() {
        return "Container with priority: " + priority;
    }

    @Override
    public boolean push(Box item) {
        if (size == boxes.length) {
            expandCapacity();
        }

        boxes[++top] = item;
        size++;
        return true;
    }
    private void expandCapacity() {
        int newCapacity = boxes.length * 2;
        boxes = Arrays.copyOf(boxes, newCapacity);
    }

    @Override
    public Box pop() {
        if (top == -1) {
            return null;
        }
        Box poppedItem = boxes[top];
        boxes[top--] = null;
        size--;
        return poppedItem;
    }

    @Override
    public void setNext(Object item) {
        if (item instanceof Container) {
            this.next = (Container) item;
        }
    }

    @Override
    public Object getNext() {
        return next;
    }
    @Override
    public double getPriority() {
        return priority;
    }
   @Override
    public int compareTo(Container otherContainer) {
        double thisDistanceSum = getPriority();
        double otherDistanceSum = otherContainer.getPriority();
        return Double.compare(thisDistanceSum, otherDistanceSum);
    }

}
class CargoFleet implements PriorityQueue<Container>{
    private Container head;
    private int size;

    public CargoFleet() {
        this.head=null;
        this.size=0;
    }
    @Override
    public Boolean isEmpty() {
        return  size == 0;
    }

    @Override
    public Container peek() {
        return head;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Boolean enqueue(Container item) {
        if (item == null) {
            return false;  // Unable to enqueue a null item
        }
        if (isEmpty()) {
            head = item;
        } else {
            Container current = head;
            Container previous = null;
            while (current != null && item.getPriority() >= current.getPriority()) {
                previous = current;
                current = (Container) current.getNext();
                item.setNext(current);
            }
            if (previous == null) {
                item.setNext(head);
                head = item;
            } else {
                previous.setNext(item);
                item.setNext(current);
            }

        }

        size++;
        return true;
    }
    @Override
    public Container dequeue() {
        if (isEmpty()) {return null;}

        Container removed = (Container) head;
        head = (Container) head.getNext();
        removed.setNext(null);
        size--;

        return removed; }
}
class CargoCompany {
    private Container stack;
    private CargoFleet queue;

    public CargoCompany() {
        stack = new Container();
        queue = new CargoFleet();
    }


//?extends
    public <T extends Box<?>> void add(T box) {
        if (stack.push(box)) {}
        else if (queue.enqueue(stack)) {
            stack = new Container();
            add(box);
        } else {
            ship(queue);
            add(box);
        }
    }

    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            Container container = fleet.dequeue();
            empty(container);
        }
    }

    private void empty(Container container) {
        Box box;
        while ((box = container.pop()) != null) {
            Sellable content = deliver(box);
            System.out.println("Delivered: " + content);
        }
    }
    private <T> Sellable deliver(T box) {
        if (box instanceof Box<?>) {
            Box<?> boxObj = (Box<?>) box;
            Object content = boxObj.getContent();
            if (content instanceof Sellable) {
                return (Sellable) content;
            }        }        return null;
    }
}
