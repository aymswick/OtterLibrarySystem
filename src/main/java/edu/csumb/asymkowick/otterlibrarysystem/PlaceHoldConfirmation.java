package edu.csumb.asymkowick.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PlaceHoldConfirmation extends ActionBarActivity implements View.OnClickListener{

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();
    Bundle placeHoldInfo;
    private SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold_confirmation);

        View yes = findViewById(R.id.yesAdd);
        yes.setOnClickListener(this);

        View no = findViewById(R.id.noAdd);
        no.setOnClickListener(this);

        placeHoldInfo = getIntent().getExtras();
        Long checkDate = placeHoldInfo.getLong("checkoutDate");
        Long returnDate = placeHoldInfo.getLong("returnDate");
        Book myBook = placeHoldInfo.getParcelable("book");
        Customer myCustomer = placeHoldInfo.getParcelable("customer");
        Date date = new Date();

        long difference = returnDate - checkDate;
        difference /= 1000;
        difference /= 60;
        difference /= 60;

        dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.US);

        DateFormat transDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat transTimeFormat = new SimpleDateFormat("h:mm a");

        Reservation test = new Reservation(myCustomer, myBook, checkDate, returnDate);
        String resNum = Integer.toString(test.getReservationNumber());


        otterLibrary.addToLogs("----------------", "---------------");
        otterLibrary.addToLogs("Transaction Type: ", "Place Hold");
        otterLibrary.addToLogs("Customer's Username: ", myCustomer.getUsername());
        otterLibrary.addToLogs("Book Title: ", myBook.getTitle());
        otterLibrary.addToLogs("Pickup Date: ", dateFormat.format(checkDate));
        otterLibrary.addToLogs("Return Date: ", dateFormat.format(returnDate));
        otterLibrary.addToLogs("Reservation Number: ", resNum);
        otterLibrary.addToLogs("Transaction Date: ", transDateFormat.format(date)); //double check this
        otterLibrary.addToLogs("Transaction Time: ", transTimeFormat.format(date));
        otterLibrary.addToLogs("----------------", "---------------");

        System.out.println(myBook.getFee());

        TextView holdInfo = (TextView) findViewById(R.id.HoldInfo);
        holdInfo.setText(
                "Username: " + myCustomer.getUsername() + "\n" +
                "Pickup Date: " + dateFormat.format(checkDate) + "\n" +
                "Return Date: " + dateFormat.format(returnDate) + "\n" +
                "Reservation Number: " + resNum + "\n" +
                "Book Title: " + myBook.getTitle() + "\n" +
                "Total Amount Owed: " + moneyFormat((myBook.getFee() * difference))
        );

    }



    public void onClick(View v) {
        if(v.getId() == R.id.yesAdd)
        {
            Long checkDate = placeHoldInfo.getLong("checkoutDate");
            Long returnDate = placeHoldInfo.getLong("returnDate");
            Book myBook = placeHoldInfo.getParcelable("book");
            Customer myCustomer = placeHoldInfo.getParcelable("customer");

            Reservation newRes = new Reservation(myCustomer, myBook, checkDate, returnDate);
            otterLibrary.addReservation(newRes);
            otterLibrary.removeBook(myBook);
            System.out.println("REMOVING BOOK");

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        else if(v.getId() == R.id.noAdd)
        {
            Toast.makeText(this, "Please press the back button and edit your info.", Toast.LENGTH_LONG);
        }
    }


    public String moneyFormat(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formattedAmount = formatter.format(amount);
        return formattedAmount;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_hold_confirmation, menu);
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
