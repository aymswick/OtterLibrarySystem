package edu.csumb.asymkowick.otterlibrarysystem;

import android.app.Application;

/**
 * Title: MyApp.java
 * Abstract:
 *            This class grabs control of the application at startup to create an instance of a database;
 *            by doing this, the database can be initialized with the correct info and updated in ANY activity.
 *            According to Dr. Byun, database must persist only while the app is running.  While this app is running,
 *            any activity can view and update information "live" so that other activities see the changes.
 *
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 5/1/15.
 */








public class MyApp extends Application {

    private LibrarySystem myGloballyAccessibleObject; //make getter and setter
    private static MyApp singleInstance = new MyApp();

    public static MyApp getInstance()
    {
        return singleInstance;
    }

    public MyApp() {
        myGloballyAccessibleObject = new LibrarySystem();

        Customer alice = new Customer();
        alice.setUsername("a@lice5");
        alice.setPassword("@csit100");

        Customer brian = new Customer();
        brian.setUsername("$brian7");
        brian.setPassword("123abc##");

        Customer chris = new Customer();
        chris.setUsername("!chris12!");
        chris.setPassword("CHRIS12!!");

        Customer admin = new Customer();
        admin.setUsername("!admin2");
        admin.setPassword("!admin2");

        myGloballyAccessibleObject.addCustomer(alice);
        myGloballyAccessibleObject.addCustomer(brian);
        myGloballyAccessibleObject.addCustomer(chris);
        myGloballyAccessibleObject.addCustomer(admin);

        Book book1 = new Book();
        book1.setTitle("Hot Java");
        book1.setAuthor("S. Narayanan");
        book1.setIsbn("123-ABC-101");
        book1.setFee(0.05);

        Book book2 = new Book();
        book2.setTitle("Fun Java");
        book2.setAuthor("Y. Byun");
        book2.setIsbn("ABCDEF-09");
        book2.setFee(1.00);

        Book book3 = new Book();
        book3.setTitle("Algorithm for Java");
        book3.setAuthor("K. Alice");
        book3.setIsbn("CDE-777-123");
        book3.setFee(.25);

        myGloballyAccessibleObject.addBook(book1);
        myGloballyAccessibleObject.addBook(book2);
        myGloballyAccessibleObject.addBook(book3);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
    }

    public LibrarySystem getDatabase() {
        return myGloballyAccessibleObject;
    }

    public void setDatabase(LibrarySystem toBeGlobalized) {
        this.myGloballyAccessibleObject = toBeGlobalized;
    }
}