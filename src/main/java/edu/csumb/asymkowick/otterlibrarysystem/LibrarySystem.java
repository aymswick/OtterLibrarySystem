package edu.csumb.asymkowick.otterlibrarysystem;
import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by asymkowick on 4/22/15.
 */
public class LibrarySystem implements Parcelable {

    String title;
    ArrayList <Customer> customers;
    int numOfCustomers;
    int numOfBooks;
    int numOfRes;
    ArrayList<Book> books = new ArrayList<Book>(20);
    ArrayList<String> logs = new ArrayList<String>(); //this is the log file
    ArrayList<Reservation> reservations = new ArrayList<Reservation>(20);

    ArrayList<Book> availableBooks = new ArrayList<>();


    public LibrarySystem() {
        customers = new ArrayList(20);
        title = "Otter Library";
        numOfCustomers = 0;
        numOfBooks = 0;
    }

    private LibrarySystem(Parcel in) {

        customers = new ArrayList(20);
        title = in.readString();
        numOfCustomers = in.readInt();
        //System.out.println("CUSTOMERS SIZE IS : " + customers.size());
        //System.out.println("CUSTOMERS SIZE SHOULD BE: " + numOfCustomers);
        //customers = in.readArrayList(Customer.class.getClassLoader());
        in.readList(customers, Customer.class.getClassLoader());
        numOfBooks = in.readInt();
        //books = in.readArrayList(Book.class.getClassLoader());
        in.readList(books, Book.class.getClassLoader());



    }

    public void addCustomer(Customer myCustomer) {
        customers.add(myCustomer);
        numOfCustomers++;
    }

    public void addBook(Book myBook) {
        books.add(myBook);
        availableBooks.add(myBook);
        numOfBooks++;
    }

    public void removeBook(Book myBook) {
        Book r = new Book(); //to be Removed

        for(Book temp : books)
        {
            if(temp.getTitle().equals(myBook.getTitle()))
            {
                r = temp;
            }
        }

        availableBooks.remove(r);
    }

    public void addToLogs(String title, String content) {
        this.logs.add(title + (content + "\n"));
    }

    public void addReservation(Reservation newRes) {
        reservations.add(newRes);
        numOfRes++;
    }

    public ArrayList<String> getLogs() {
        return this.logs;
    }



    @Override
    public String toString() {
        String sysName = "System Name: " + title;
        String custInfo = "Customers: ";
        String bookInfo = "Books: ";




        for(Customer temp : customers)
        {
            custInfo += temp.getUsername();
            custInfo += ", ";
        }


        for(Book temp : books)
        {
            bookInfo += temp.getTitle();
            bookInfo += ", ";
        }

        String two = "Customers: " + customers;
        String three = "Books: " + books;


      return sysName + "\n" + custInfo + "\n" + bookInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(numOfCustomers);

        if(customers.size() > 0)
        dest.writeList(customers);

        dest.writeInt(numOfBooks);

        if(books.size() > 0)
        dest.writeList(books);


    }


    public static final Parcelable.Creator CREATOR = new LibrarySystem.Creator() {

        @Override
        public LibrarySystem createFromParcel(Parcel in) {
            return new LibrarySystem(in);
        }

        @Override
        public LibrarySystem[] newArray(int size) {
            return new LibrarySystem[size];
        }

    };





}
