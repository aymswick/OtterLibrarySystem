package edu.csumb.asymkowick.otterlibrarysystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class ManageSystem extends ActionBarActivity implements View.OnClickListener {

    //HorizontalScrollView display = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);


        View showButton = findViewById(R.id.show_button);
        showButton.setOnClickListener(this);




        View mainMenu = findViewById(R.id.ok);
        mainMenu.setVisibility(View.INVISIBLE);
        mainMenu.setOnClickListener(this);
    }

    public void onClick(View v) {
        TextView display = (TextView) findViewById(R.id.log_text);
        ArrayList<String> logs = otterLibrary.getLogs();

        StringBuilder logBuilder = new StringBuilder();
        View showButton = findViewById(R.id.show_button);

        View ok = findViewById(R.id.ok);
        ok.setVisibility(View.INVISIBLE);

        for (String value : logs)
        {
            logBuilder.append(value);
        }


        String text = logBuilder.toString();

        if(v.getId() == R.id.show_button)
        {
            showButton.setVisibility(View.INVISIBLE);
            ok.setVisibility(View.VISIBLE);
            display.setText(logBuilder);

        }

        else if(v.getId() == R.id.ok)
        {
            Intent i = new Intent(this, AddBook.class);
            startActivity(i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_system, menu);
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
