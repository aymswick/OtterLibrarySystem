package edu.csumb.asymkowick.otterlibrarysystem;
/**
 * Title: AdminLogin.java
 * Abstract: This activity runs when ADMIN needs to log in to their account to manage the system.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 4/22/15.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class AdminLogin extends ActionBarActivity implements View.OnClickListener{

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);





        View nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);

        View loadingCircle = findViewById(R.id.loading_circle);
        loadingCircle.setVisibility(View.INVISIBLE);

        Toast.makeText(this, otterLibrary.toString(), Toast.LENGTH_LONG).show();
    }


    public void onClick(View v) {
        EditText username = (EditText)findViewById(R.id.username_edittext);
        EditText password = (EditText)findViewById(R.id.password_edittext);

        Customer newCustomer = new Customer();
        newCustomer.setUsername(username.getText().toString());
        newCustomer.setPassword(password.getText().toString());

        /*
        Customer testAlice = new Customer();
        testAlice.setUsername("a@lice5");
        testAlice.setPassword("@csit100");

        Boolean kek, bek;
        kek = otterLibrary.customers.get(0).equals(testAlice);
        System.out.println("object equality test: " + kek);
        bek = otterLibrary.customers.contains(testAlice);
        System.out.println("array contains test: " + bek);


        System.out.println("OLB User: " + otterLibrary.customers.get(0).getUsername());
        System.out.println("OLB Pass: " + otterLibrary.customers.get(0).getPassword());

        System.out.println("");
        System.out.println("Login User: " + newCustomer.getUsername());
        System.out.println("Login Pass: " + newCustomer.getPassword());
        */



        if(v.getId() == R.id.next_button)
        {
            if(newCustomer.getUsername().equals("!admin2") && newCustomer.getPassword().equals("!admin2"))
            {
                Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ManageSystem.class);
                startActivity(i);
            }

            else
            {
                Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show();
                System.out.println("Admin not found.");
            }
        }

        else if(v.getId() == R.id.username_edittext)
        {
            username = (EditText)findViewById(R.id.username_edittext);
        }

        else if(v.getId() == R.id.password_edittext)
        {
            password = (EditText)findViewById(R.id.password_edittext);
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
