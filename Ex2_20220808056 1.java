//irem since april 1
import java.util.Date;
public class Ex2_20220808056 {

}
class Ticket{
    private City from;
    private City to;
    private Date date;
    private int seat;

    public Ticket(int seat,Date date,City from,City to){
        this.seat = seat;
        this.date = date;
        this.from = from;
        this.to = to;
    }
    public Ticket(int seat,City from,City to){
        this.seat = seat;
        this.date=new Date(System.currentTimeMillis()+86400000);
        this.from = from;
        this.to = to;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }


}
class City{
    private String postalCode;
    private String name;

    public City(String postalCode,String name){
        this.name=name;
        this.postalCode=postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getName() {
        return name;
    }
}
class Person{
    private String name;
    private String surname;
    private String phoneNumber;

    public Person(String phoneNumber,String name,String surname){
        this.name=name;
        this.surname=surname;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
