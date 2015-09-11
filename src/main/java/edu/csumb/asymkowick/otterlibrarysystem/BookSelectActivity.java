package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: BookSelectActivity.java
 * Abstract:  This activity runs when a user has selected a checkout and return date in PlaceHold.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 5/1/15.
 */



import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class BookSelectActivity extends ActionBarActivity implements View.OnClickListener {

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();

    Bundle placeHoldInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select);
        Spinner bookSpinner = (Spinner) findViewById(R.id.bookSpinner);
        View toLoginButton = findViewById(R.id.toLoginButton);
        toLoginButton.setOnClickListener(this);



        placeHoldInfo = getIntent().getExtras();
        Long checkDate = placeHoldInfo.getLong("checkoutDate");
        Long returnDate = placeHoldInfo.getLong("returnDate");





        ArrayList<Book> books = otterLibrary.availableBooks;



        List <String> titleList = new ArrayList<String>();
        for(int i = 0; i < books.size(); i++)
        {
            titleList.add(books.get(i).getTitle());
        }


        ArrayList <String> infoList = new ArrayList<String>();
        for(Book temp : books)
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
                TextView author = (TextView) findViewById(R.id.authorTextView);
                TextView isbn = (TextView) findViewById(R.id.isbnTextView);
                TextView fee = (TextView) findViewById(R.id.feeTextView);

                String title =  parent.getItemAtPosition(position).toString();
                System.out.println(title);

                for(Book temp : otterLibrary.availableBooks)
                {
                    if(temp.getTitle().equals(title))
                    {
                        author.setText("Author: " + temp.getAuthor());
                        isbn.setText("ISBN: " + temp.getIsbn());
                        fee.setText("Hourly Fee: " + temp.getFormattedFee());
                        placeHoldInfo.putParcelable("book", temp);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }



    public void onClick(View v) {

        if(v.getId() == R.id.toLoginButton)
        {
            Intent i = new Intent(this, Login.class);
            i.putExtras(placeHoldInfo);
            startActivity(i);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_select, menu);
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
