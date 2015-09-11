package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: CreateAccount.java
 * Abstract: This activity runs when Create Account button is selected at MainActivity.  It allows user to create an account.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 4/22/15.
 */


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.*;
import java.lang.System;
import android.app.AlertDialog;


public class CreateAccount extends ActionBarActivity implements View.OnClickListener {

    boolean rulesFlag = false;
    boolean dupesFlag = false;

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        View nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        View loadingCircle = findViewById(R.id.loading_circle);
        loadingCircle.setVisibility(View.INVISIBLE);


        ActionBar actionBar = getSupportActionBar();
        actionBar.show();



        //Toast.makeText(this, otterLibrary.toString(), Toast.LENGTH_LONG).show();

    }


    public void onClick(View v) {
        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());

        if(v.getId() == R.id.next_button)
        {
            v.setVisibility(View.INVISIBLE);
            View loadingCircle = findViewById(R.id.loading_circle);
            loadingCircle.setVisibility(View.VISIBLE);


            EditText username = (EditText)findViewById(R.id.username_edittext);
            EditText password = (EditText)findViewById(R.id.password_edittext);

            Intent previousActivity = getIntent();
            //OLD METHOD FOR DB
           // LibrarySystem otterLibrary = previousActivity.getParcelableExtra("LibrarySystem");


            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("h:mm a");
            Date date = new Date();
            System.out.println(dateFormat.format(date));


            if(checkAccountRules(username, password))
            {
                System.out.println("rules success");
                if(checkAccountDuplicates(otterLibrary, username))
                {
                    System.out.println("total success");
                    Intent i = new Intent(this, AccountSuccess.class);
                    Customer toBeAdded = new Customer();
                    toBeAdded.setUsername(username.getText().toString());
                    toBeAdded.setPassword(password.getText().toString());
                    otterLibrary.addCustomer(toBeAdded);

                    //Log CreateAccount info
                    otterLibrary.addToLogs("----------------", "---------------");
                    otterLibrary.addToLogs("Transaction Type: ", "Create Account");
                    otterLibrary.addToLogs("Customer's Username: ", toBeAdded.getUsername());
                    otterLibrary.addToLogs("Transaction Date: ", dateFormat.format(date)); //double check this
                    otterLibrary.addToLogs("Transaction Time: ", timeFormat.format(date));
                    otterLibrary.addToLogs("----------------", "---------------");



                    System.out.println(otterLibrary.getLogs().toString());


                    //i.putExtra("LibrarySystem", otterLibrary); //old method
                    startActivity(i);
                    loadingCircle.setVisibility(View.INVISIBLE);
                    v.setVisibility(View.VISIBLE);

                }

                else
                {

                    Toast.makeText(this, "Username already taken.", Toast.LENGTH_SHORT).show();

                    if(dupesFlag == true)
                    {
                        Toast.makeText(this, "You have been sent back to the main menu.", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(this, MainActivity.class);
                        startActivity(main);
                    }

                    dupesFlag = true; //user got rules wrong at least once
                    loadingCircle.setVisibility(View.INVISIBLE);
                    v.setVisibility(View.VISIBLE);
                }
            }

            else
            {
                Toast.makeText(this, "Invalid Username or Password, Check the Rules!", Toast.LENGTH_SHORT).show();

                if(rulesFlag == true)
                {
                    Toast.makeText(this, "You have been sent back to the main menu.", Toast.LENGTH_LONG).show();
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);
                }

                rulesFlag = true;
                loadingCircle.setVisibility(View.INVISIBLE);
                v.setVisibility(View.VISIBLE);
            }
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean checkAccountRules(EditText user, EditText pass) {
        //return true if:
       //1. one number
       //2. three letters
       //3. one symbol (!$#@)

        String username = user.getText().toString();
        String password = pass.getText().toString();

        System.out.println(username);
        System.out.println(password);
        System.out.println(validate(username));
        System.out.println(validate(password));

        if(validate(username) && validate(password))
            return true;

        else
        {

            return false;
        }

    }


    public boolean validate(String UsernameOrPassword) {
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z]{3,})(?=.*[@#$!]).{5,}"; //regEx rules
        return UsernameOrPassword.matches(pattern);
    }

    public boolean checkAccountDuplicates(LibrarySystem database, EditText user) { //returns false if duplicates are found

        String username = user.getText().toString();

        for(Customer temp : database.customers)
        {
            System.out.println(temp.getUsername());
            System.out.println(username);


            if(temp.getUsername().equals(username))
            {
                return false;
            }
        }


        return true;



    }
}
