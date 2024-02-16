import java.util.*;
//irem since april 1
public class Ex3_20220808056 {

}
class Author{
    private String name;
    private String surname;
    private String mail;
    Author (String name,String surname,String mail){
        this.mail=mail;
        this.name=name;
        this.surname=surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getName() {
        return name;
    }

}
class Book{
    private String isbn,title;
    private Author author;
    private double price;

    public Book(String isbn,String title,Author author,double price) {
        this.isbn = isbn;
        this.author=author;
        this.price=price;
        this.title=title;
    }
    public Book(String isbn,String title,Author author) {
        this.isbn = isbn;
        this.author=author;
        price=15.25;
        this.title=title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    //?
    public Author getAuthor() {
        return author;
    }

}
class EBook extends Book{
 private String downloadUrl;
 private double sizeMb;
 public EBook (String isbn,String title,Author author,double price,String downloadUrl,double sizeMb){
     super(isbn,title,author,price);
     this.downloadUrl=downloadUrl;
     this.sizeMb=sizeMb;
 }
 public EBook (String isbn,String title,Author author,String downloadUrl,double sizeMb){
        super(isbn,title,author);
        this.downloadUrl=downloadUrl;
        this.sizeMb=sizeMb;
    }
    public String getDownloadUrl(){
        return downloadUrl;
 }
    public double getSizeMb() {
        return sizeMb;
    }
}
class PaperBook extends Book{
    private int shippingWeight;
    private boolean inStock;
    public PaperBook(String isbn,String title,Author author,double price,int shippingWeight,boolean inStock){
        super(isbn,title,author,price);
        this.shippingWeight=shippingWeight;
        this.inStock=inStock;}

    public PaperBook(String isbn,String title,Author author,boolean inStock){
            super(isbn,title,author);
            Random random = new Random();
            shippingWeight = random.nextInt(11) + 5;
            this.inStock=inStock;
}

    public int getShippingWeight() {
        return shippingWeight;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}

