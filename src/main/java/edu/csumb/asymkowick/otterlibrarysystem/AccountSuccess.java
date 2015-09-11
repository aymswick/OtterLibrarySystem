package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: AccountSuccess.java
 * Abstract:
 *            This activity runs when a user has created an account at CreateAccount that meets the
 *            requirements for username and password content.
 *
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 5/1/15.
 */



import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class AccountSuccess extends ActionBarActivity implements View.OnClickListener {

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_success);


        ImageView checkmark = (ImageView) findViewById(R.id.checkmark);


        checkmark.setImageResource(R.mipmap.checkmark);

        TextView users = (TextView) findViewById(R.id.display_users);
       // users.setText(otterLibrary.toString());

        View main_menu = findViewById(R.id.home);
        main_menu.setOnClickListener(this);

    }

    public void onClick(View v) {
        if(v.getId() == R.id.home)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i); //LEFT OFF HERE
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_success, menu);
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
