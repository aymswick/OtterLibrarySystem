package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: CancelHold.java
 * Abstract: This activity runs when Cancel Hold button is selected at MainActivity.  It allows a user to cancel their previous hold on a book.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 4/22/15.
 */


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class CancelHold extends ActionBarActivity implements View.OnClickListener{

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);



        View nextButton = findViewById(R.id.login);
        nextButton.setOnClickListener(this);


    }


    public void onClick(View v) {
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        Customer newCustomer = new Customer();
        newCustomer.setUsername(username.getText().toString());
        newCustomer.setPassword(password.getText().toString());



        if(v.getId() == R.id.login)
        {
            if(otterLibrary.customers.contains(newCustomer))
            {

                Boolean empty = true;

                Bundle cancelHoldInfo = new Bundle();
                Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show();

                for(Reservation temp : otterLibrary.reservations)
                {
                    if(newCustomer.equals(temp.getReservedBy()))
                    {
                        empty = false;
                    }
                }

                if(empty == false)
                {
                    Intent i = new Intent(this, BookSelectCancelActivity.class);
                    cancelHoldInfo.putParcelable("customer", newCustomer);
                    i.putExtras(cancelHoldInfo);
                    startActivity(i);
                }

                else
                {
                    Intent j = new Intent(this, CancelHoldFail.class);
                    startActivity(j);
                }











            }

            else
            {
                Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show();
                System.out.println("Customer not found.");
            }
        }

        else if(v.getId() == R.id.username)
        {
            username = (EditText)findViewById(R.id.username);
        }

        else if(v.getId() == R.id.password)
        {
            password = (EditText)findViewById(R.id.password);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
