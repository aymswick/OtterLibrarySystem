package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: MainActivity.java
 * Abstract: This activity runs when the app is opened.  It displays four buttons to a user: Create Account, Place Hold, Cancel Hold, and Manage System.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 4/22/15.
 */


import android.app.Application;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




public class MainActivity extends ActionBarActivity implements OnClickListener {

        //LibrarySystem db = createDatabase(); //builds a LibrarySystem with desired customers and books pre-registered
        MyApp database = MyApp.getInstance();
        LibrarySystem otterLibrary = database.getDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(this);

        View placeHoldButton = findViewById(R.id.place_hold_button);
        placeHoldButton.setOnClickListener(this);

        View cancelHoldButton = findViewById(R.id.cancel_hold_button);
        cancelHoldButton.setOnClickListener(this);

        View manageSystemButton = findViewById(R.id.manage_system_button);
        manageSystemButton.setOnClickListener(this);



        //ActionBar actionBar = getSupportActionBar();
        //actionBar.show();

        /**
         * Following Code is for
         * Debugging / Development Use Only!
         */

        /**
        Intent toBeSent = new Intent(this, show_contents.class);
        toBeSent.putExtra("test", alice);
        startActivity(toBeSent);
        */



    }

    public void onClick(View v) {


        if(v.getId() == R.id.create_account_button)
        {
            Intent i = new Intent(this, CreateAccount.class);
            //sendDatabaseToActivity(db, i);
            startActivity(i);
        }

        if(v.getId() == R.id.place_hold_button)
        {
            Intent i = new Intent(this, PlaceHold.class);
            //sendDatabaseToActivity(db, i);
            startActivity(i);
        }

        if(v.getId() == R.id.cancel_hold_button)
        {
            Intent i = new Intent(this, CancelHold.class);
            //sendDatabaseToActivity(db, i);
            startActivity(i);
        }

        if(v.getId() == R.id.manage_system_button)
        {
            Intent i = new Intent(this, AdminLogin.class);
            //sendDatabaseToActivity(db, i);
            startActivity(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent debug = new Intent(this, show_contents.class);
            startActivity(debug);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    public LibrarySystem createDatabase() {
        //Create internal database
        LibrarySystem otterLibrary = new LibrarySystem();

        Customer alice = new Customer();
        alice.setUsername("a@lice5");
        alice.setPassword("@csit100");

        Customer brian = new Customer();
        brian.setUsername("$brian7");
        brian.setPassword("123abc##");

        Customer chris = new Customer();
        chris.setUsername("!chris12!");
        chris.setPassword("CHRIS12!!");

        otterLibrary.addCustomer(alice);
        otterLibrary.addCustomer(brian);
        otterLibrary.addCustomer(chris);

        Book book1 = new Book();
        book1.setTitle("Hot Java");
        book1.setAuthor("S. Narayanan");
        book1.setIsbn("123-ABC--101");
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

        otterLibrary.addBook(book1);
        otterLibrary.addBook(book2);
        otterLibrary.addBook(book3);

        return otterLibrary;
    }

    */

    public void sendDatabaseToActivity(LibrarySystem database, Intent toBeSent) { //for old style of parceling the database through activities, good to learn, not best method to use in this case -- Switched to internal database
        toBeSent.putExtra("LibrarySystem", database);
        startActivity(toBeSent);
    }

    public void writeInitialDatabaseToXML(LibrarySystem otterLibrary) { //this was difficult to get working, will spend more time on XML over summer when there is no time constraint -- Switched to internal database
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;




        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElementNS("edu.csumb.asymkowick", "Customers");
            doc.appendChild(mainRootElement);


            mainRootElement.appendChild(getCustomer(doc, "1", otterLibrary.customers.get(0).getUsername(),
                                                    otterLibrary.customers.get(0).getPassword()));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            System.out.println("\nXML DOM Created Successfully..");

        }





        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getCustomer(Document doc, String id, String name, String password) {
        Element customer = doc.createElement("Customer");
        customer.setAttribute("id", id);
        customer.appendChild(getCustomerElements(doc, customer, "Name", name));
        customer.appendChild(getCustomerElements(doc, customer, "Password", password));
        return customer;
    }

    // utility method to create text node
    private static Node getCustomerElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}
