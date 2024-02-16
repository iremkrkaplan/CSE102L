import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
public class Ex6_20220808056 {
}
abstract class Product implements Comparable<Product>{
    private String name;
    private double price;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public Product() {
    }

    public Product(String name, double price, int quantity) {
        this.name=name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int compareTo(Product other) {
        return Double.compare(price, other.price);
    }

    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", price=" + price + "]";
    }
}
abstract class Book extends Product {
    private String author;
    private int pageCount;
    public Book(String name, double price, String author, int pageCount) {
        super(name, price);
        this.author = author;
        this.pageCount = pageCount;
    }
    public Book() {
    }
    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }
}
class ReadingBook extends Book{
    private String genre;
    ReadingBook(String name, double price, String author, int pageCount,String genre){
        super(name,price,author,pageCount);
        this.genre=genre;
    }
    public String getGenre(){
        return genre;
    }
}
class ColoringBook extends Book implements Colorable{
    private String color;
    ColoringBook(String name, double price, String author, int pageCount,String color ){
    super(name,price,author,pageCount);
    this.color=color;
}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

class Toyhorse extends Product implements Rideable{
    public ToyHorse(String name, double price) {
        super(name, price);
    }
    public void ride() {}
}
public class Bicycle extends Product implements Colorable, Rideable {
    private String color;

    public Bicycle(String name, double price, int quantity, String color) {
        super(name, price, quantity);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void ride() {
        System.out.println("Riding the bicycle!");
    }

    public void paint(String color) {
        setColor(color);
        System.out.println("Painted the bicycle " + color + "!");
    }
}


class User {
    private String username;
    private String email;
    private Collection<Product> cart;
    private PaymentMethod payment;
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.payment = null;
        this.cart = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }
    public Product getProduct(int index){
        return ((ArrayList<Product>) cart).get(index);
    }
    public void removeProduct(int index) {
        ((ArrayList<Product>) cart).remove(index);
    }

    public void addProduct(Product product) {
        cart.add(product);
    }

    public void purchase() throws PaymentFailedException {
        if (payment == null) {
            throw new IllegalStateException("Payment method not set");
        }

        double total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        if (!payment.pay(total)) {
            throw new PaymentFailedException("Payment failed");
        }

        cart.clear();
    }
}

public class CreditCard implements PaymentMethod {
    private long cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String cardHolderName, Date expirationDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public boolean pay(double amount) {
        // Here goes the implementation of the pay method using credit card information
        // Return true if payment is successful, false otherwise
        return false;
    }
}
 class PayPal implements PaymentMethod {
    private String username;
    private String password;

    public PayPal(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("account"+username+"payed"+amount);
        return true;
    }
}

class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}

interface Rideable {
    void ride();
}
interface PaymentMethod {
    boolean pay(double amount);
}

interface Colorable {
    void paint(String color);
}




