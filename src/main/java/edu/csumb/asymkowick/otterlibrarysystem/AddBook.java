package edu.csumb.asymkowick.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddBook extends ActionBarActivity implements View.OnClickListener {



    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        View yesBtn = findViewById(R.id.yesAdd);
        View noBtn = findViewById(R.id.noAdd);
        View title = findViewById(R.id.title);
        View author = findViewById(R.id.author);
        View isbn = findViewById(R.id.isbn);
        View fee = findViewById(R.id.fee);
        View addBook = findViewById(R.id.AddBook);

        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);
        addBook.setOnClickListener(this);

        title.setVisibility(View.INVISIBLE);
        author.setVisibility(View.INVISIBLE);
        isbn.setVisibility(View.INVISIBLE);
        fee.setVisibility(View.INVISIBLE);
        addBook.setVisibility(View.INVISIBLE);






    }


    public void onClick(View v) {
        View yesBtn = findViewById(R.id.yesAdd);
        View noBtn = findViewById(R.id.noAdd);
        EditText title = (EditText) findViewById(R.id.title);
        EditText author = (EditText) findViewById(R.id.author);
        EditText isbn = (EditText) findViewById(R.id.isbn);
        EditText fee = (EditText) findViewById(R.id.fee);
        View addBook = findViewById(R.id.AddBook);




        if(v.getId() == R.id.yesAdd)
        {
            yesBtn.setVisibility(View.INVISIBLE);
            noBtn.setVisibility(View.INVISIBLE);
            title.setVisibility(View.VISIBLE);
            author.setVisibility(View.VISIBLE);
            isbn.setVisibility(View.VISIBLE);
            fee.setVisibility(View.VISIBLE);
            addBook.setVisibility(View.VISIBLE);


        }

        else if(v.getId() == R.id.noAdd)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        else if(v.getId() == R.id.AddBook)
        {
            Book addThisBook = new Book();
            addThisBook.setTitle(title.getText().toString());
            addThisBook.setAuthor(author.getText().toString());
            addThisBook.setIsbn(isbn.getText().toString());
            String money = (fee.getText().toString());
            addThisBook.setFee(Double.parseDouble(fee.getText().toString()));

            Boolean duplicate = false; // this flag will be triggered if the book is already in the system.

            for(Book temp : otterLibrary.books)
            {
                if(temp.getTitle().equals(addThisBook.getTitle()))
                {
                    duplicate = true;
                }

            }

            if(title.getText().toString().equals("") || author.getText().toString().equals("") || isbn.getText().toString().equals("") || fee.getText().toString().equals("")
                    || duplicate == true)
            {
                Toast.makeText(this, "Invalid Book Information -- Verify that all fields are filled out and book is not already in system.", Toast.LENGTH_LONG).show();
                Intent j = new Intent(this, MainActivity.class);
                startActivity(j);
            }

            else
            {
                otterLibrary.addBook(addThisBook);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
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
