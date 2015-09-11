package edu.csumb.asymkowick.otterlibrarysystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BookSelectCancelActivity extends ActionBarActivity implements View.OnClickListener {


    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();

    Bundle received;
    Customer myCustomer;
    Book myBook;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select_cancel);

        Spinner bookSpinner = (Spinner) findViewById(R.id.bookSpinner);
        View select = findViewById(R.id.confirm);
        select.setOnClickListener(this);


        received = getIntent().getExtras();
        myCustomer = received.getParcelable("customer");
        ArrayList<Book> booksForSpinner = new ArrayList<>();

        for(Reservation temp : otterLibrary.reservations)
        {
            if(myCustomer.equals(temp.getReservedBy()))
            {
                booksForSpinner.add(temp.getReservedBook());
            }
        }

        List<String> titleList = new ArrayList<String>();
        for(int i = 0; i < booksForSpinner.size(); i++)
        {
            titleList.add(booksForSpinner.get(i).getTitle());
        }


        ArrayList <String> infoList = new ArrayList<String>();
        for(Book temp : booksForSpinner)
        {
            infoList.add("Author: " + temp.getAuthor());
            infoList.add("ISBN: " + temp.getIsbn());
            infoList.add("Hourly Fee: " + temp.getFormattedFee());
        }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, titleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSpinner.setAdapter(dataAdapter);
        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView author = (TextView) findViewById(R.id.textView5);
                TextView isbn = (TextView) findViewById(R.id.textView7);
                TextView fee = (TextView) findViewById(R.id.textView8);

                String title =  parent.getItemAtPosition(position).toString();
                System.out.println(title);

                for(Book temp : otterLibrary.books)
                {
                    if(temp.getTitle().equals(title))
                    {
                        author.setText("Author: " + temp.getAuthor());
                        isbn.setText("ISBN: " + temp.getIsbn());
                        fee.setText("Hourly Fee: " + temp.getFormattedFee());
                        myBook = temp;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    public void onClick(View v) {
        if(v.getId() == R.id.confirm)
        {
            //add code to remove the reservation



            dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.US);

            DateFormat transDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat transTimeFormat = new SimpleDateFormat("h:mm a");

            Long checkDate = null;
            Long returnDate = null;
            Date date = new Date();

            String resNum = "";

            for(Reservation temp : otterLibrary.reservations)
            {
                if(myBook.equals(temp.getReservedBook()))
                {
                    System.out.println("they are equal");
                    otterLibrary.availableBooks.add(myBook); //this should let the book be available for a hold again
                    checkDate = temp.getCheckoutDate();
                    returnDate = temp.getReturnDate();
                    resNum = Integer.toString(temp.getReservationNumber());
                }
            }


            otterLibrary.addToLogs("----------------", "---------------");
            otterLibrary.addToLogs("Transaction Type: ", "Cancel Hold");
            otterLibrary.addToLogs("Customer's Username: ", myCustomer.getUsername());
            otterLibrary.addToLogs("Book Title: ", myBook.getTitle());
            otterLibrary.addToLogs("Pickup Date: ", dateFormat.format(checkDate));
            otterLibrary.addToLogs("Return Date: ", dateFormat.format(returnDate));
            otterLibrary.addToLogs("Reservation Number: ", resNum);
            otterLibrary.addToLogs("Transaction Date: ", transDateFormat.format(date)); //double check this
            otterLibrary.addToLogs("Transaction Time: ", transTimeFormat.format(date));
            otterLibrary.addToLogs("----------------", "---------------");


            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_select_cancel, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
