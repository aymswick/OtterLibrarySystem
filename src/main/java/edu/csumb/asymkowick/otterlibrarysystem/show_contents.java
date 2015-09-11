package edu.csumb.asymkowick.otterlibrarysystem;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class show_contents extends ActionBarActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_contents);

        View button = findViewById(R.id.button);
        button.setOnClickListener(this);


    }



    public void onClick(View v) {
        if(v.getId() == R.id.button)
        {
            Intent previousActivity = getIntent();
            //OLD METHOD FOR DB
            //LibrarySystem otterLibrary = previousActivity.getParcelableExtra("LibrarySystem");

            //NEW METHOD FOR DB
            MyApp database = MyApp.getInstance();
            LibrarySystem otterLibrary = database.getDatabase();

            Toast.makeText(this, otterLibrary.toString(), Toast.LENGTH_LONG).show();
            updateText(otterLibrary);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_contents, menu);
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


    public void updateText(LibrarySystem parceled) {
            TextView textView;
            textView = (TextView) findViewById(R.id.textView);
            textView.setText(parceled.toString());
    }


}
