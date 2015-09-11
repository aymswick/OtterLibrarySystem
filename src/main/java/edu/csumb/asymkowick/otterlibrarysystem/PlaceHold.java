package edu.csumb.asymkowick.otterlibrarysystem;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;


public class PlaceHold extends ActionBarActivity implements View.OnClickListener, com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener{

    MyApp database = MyApp.getInstance();
    LibrarySystem otterLibrary = database.getDatabase();

    Calendar todayCal = Calendar.getInstance();
    Calendar bufferCal = Calendar.getInstance();

    Date checkDate;
    Date returnDate;

    Date bufferDate = todayCal.getTime();
    Date todayDate = todayCal.getTime();

    private EditText checkoutDateText;
    private EditText returnDateText;
    private EditText checkoutTimeText;
    private EditText returnTimeText;

    private Button next;
    private DatePickerDialog checkoutDPD;
    private DatePickerDialog returnDPD;


    Calendar checkoutCal;
    Calendar returnCal;

    public Dialog checkoutDialog;
    public Dialog returnDialog;
    //public Button okButton;




    private TextView dateLabel;
    private TextView timeLabel;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Calendar calendar;
    private String determinate = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.US);


        checkoutDateText = (EditText) findViewById(R.id.checkoutDateText);
        checkoutDateText.setInputType(InputType.TYPE_NULL);
        checkoutDateText.requestFocus();
        checkoutDateText.setOnClickListener(this);

        checkoutTimeText = (EditText) findViewById(R.id.checkoutTimeText);
        checkoutTimeText.setInputType(InputType.TYPE_NULL);
        checkoutTimeText.requestFocus();
        checkoutTimeText.setOnClickListener(this);

        returnDateText = (EditText) findViewById(R.id.returnDateText);
        returnDateText.setInputType(InputType.TYPE_NULL);
        returnDateText.requestFocus();
        returnDateText.setOnClickListener(this);



        returnTimeText = (EditText) findViewById(R.id.returnTimeText);
        returnTimeText.setInputType(InputType.TYPE_NULL);
        returnTimeText.requestFocus();
        returnTimeText.setOnClickListener(this);

        next = (Button) findViewById(R.id.next_button);
        next.setOnClickListener(this);





        //setupDialogs();





        calendar = Calendar.getInstance();
        checkoutCal = Calendar.getInstance();
        returnCal = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        update();



        //setDateTimeField();

    }


    private void update() {

        if(determinate.equals("checkout"))
        {
            checkoutDateText.setText(dateFormat.format(calendar.getTime()));
            checkoutTimeText.setText(timeFormat.format(calendar.getTime()));
            checkoutCal.setTime(calendar.getTime());
            bufferCal.setTimeInMillis(checkoutCal.getTimeInMillis() + 604800000); // buffer date (returnCal must be at most 7 days later than checkCal)

        }

        else if(determinate.equals("return"))
        {
            returnDateText.setText(dateFormat.format(calendar.getTime()));
            returnTimeText.setText(timeFormat.format(calendar.getTime()));
            returnCal.setTime(calendar.getTime());
        }

    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }


    private void setupDialogs() {
        checkoutDateText.setOnClickListener(this);
        returnDateText.setOnClickListener(this);

        checkDate = new Date();
        checkDate.setTime(todayCal.getTimeInMillis()); //initialize the checkout date to today

        returnDate = new Date();
        returnDate.setTime(todayCal.getTimeInMillis()); //initialize the return date to exactly 7 days past checkout day
        System.out.println(returnDate.getTime());

        checkoutDialog = new Dialog(this);
        checkoutDialog.setContentView(R.layout.custom_dialog);
        checkoutDialog.setTitle("Checkout Time");


        TimePicker checkTimePicker = (TimePicker) checkoutDialog.findViewById(R.id.timePicker);
        DatePicker checkDatePicker = (DatePicker) checkoutDialog.findViewById(R.id.datePicker);


        Calendar now = Calendar.getInstance();
        checkoutCal = Calendar.getInstance();
        checkDatePicker.setMinDate(now.getTimeInMillis());

        checkDatePicker.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar date = Calendar.getInstance();
                        date.set(year, monthOfYear, dayOfMonth);

                        checkoutDateText.setText(dateFormat.format(date.getTime()));

                        bufferCal = Calendar.getInstance();
                        bufferCal.set(year, monthOfYear, dayOfMonth + 8);
                        checkDate = date.getTime();
                    }

                });

        checkTimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        checkTimePicker.setCurrentMinute(now.get(Calendar.MINUTE));
        checkTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                checkoutCal.set(Calendar.HOUR, hourOfDay);
                checkoutCal.set(Calendar.MINUTE, minute);
                checkoutDateText.setText(dateFormat.format(checkoutCal.getTime()));

            }
        });



        //Return Dialog


        returnDialog = new Dialog(this);
        returnDialog.setContentView(R.layout.custom_dialog);
        returnDialog.setTitle("Return Time");


        TimePicker returnTimePicker = (TimePicker) returnDialog.findViewById(R.id.timePicker);
        DatePicker returnDatePicker = (DatePicker) returnDialog.findViewById(R.id.datePicker);



        returnDatePicker.setMaxDate(checkoutCal.getTimeInMillis() + 604800000);


        returnCal = Calendar.getInstance();

        returnDatePicker.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        bufferCal = Calendar.getInstance();
                        bufferCal.set(year, monthOfYear, dayOfMonth + 8);

                        returnCal.set(Calendar.YEAR, year);
                        returnCal.set(Calendar.MONTH, monthOfYear);
                        returnCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        returnDateText.setText(dateFormat.format(returnCal.getTime()));
                    }

                });

        returnTimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        returnTimePicker.setCurrentMinute(now.get(Calendar.MINUTE));
        returnTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                returnCal.set(Calendar.HOUR, hourOfDay);
                returnCal.set(Calendar.MINUTE, minute);
                returnDateText.setText(dateFormat.format(returnCal.getTime()));

            }
        });




    }

    /*
    private void setDateTimeField() {
        checkoutDateText.setOnClickListener(this);
        returnDateText.setOnClickListener(this);


        checkDate = new Date();
        checkDate.setTime(todayCal.getTimeInMillis()); //initialize the checkout date to today

        returnDate = new Date();
        returnDate.setTime(todayCal.getTimeInMillis()); //initialize the return date to exactly 7 days past checkout day
        System.out.println(returnDate.getTime());


        //for the checkout date
        Calendar c = Calendar.getInstance();


        checkoutDPD = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();

                date.set(year, monthOfYear, dayOfMonth);

                checkoutDateText.setText(dateFormat.format(date.getTime()));

                bufferCal = Calendar.getInstance();
                bufferCal.set(year, monthOfYear, dayOfMonth + 8);

                checkDate = date.getTime();
                System.out.println("CHECKOUT: " + checkDate);
                System.out.println("TODAY: " + todayDate);

            }

        }

        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        //now for the return date

        returnDPD = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, monthOfYear, dayOfMonth);
                returnDateText.setText(dateFormat.format(date.getTime()));
                returnDate = date.getTime();

                bufferDate = bufferCal.getTime();



                System.out.println("RETURN: " + returnDate);
                System.out.println("TODAY: " + todayDate);
                System.out.println("BUFFER: " + bufferDate);


            }

        }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));







    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_hold, menu);
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


    @Override
    public void onClick(View v) {
        EditText checkDateText = (EditText) findViewById(R.id.checkoutDateText);
        EditText returnDateText = (EditText) findViewById(R.id.returnDateText);
        EditText checkTimeText = (EditText) findViewById(R.id.checkoutTimeText);
        EditText returnTimeText = (EditText) findViewById(R.id.returnTimeText);

        if(v.getId() == R.id.checkoutDateText)
        {
            //checkoutDPD.show();
            //checkoutDialog.show();
            determinate = "checkout";
            DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");

        }

        else if(v.getId() == R.id.checkoutTimeText)
        {
            determinate = "checkout";
            TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
        }

        else if(v.getId() == R.id.returnDateText)
        {
            determinate = "return";
            DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
        }

        else if(v.getId() == R.id.returnTimeText)
        {
            determinate = "return";
            TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
        }

        else if(v.getId() == R.id.next_button)
        {
            //now for the check

            /*
            if(returnDate.after(bufferDate) || returnDate.before(checkDate))
            {
                Toast.makeText(getBaseContext(), "Invalid return date: \nReturn date must be at most 7 days past checkout date.", Toast.LENGTH_LONG).show();
                //next.setEnabled(false);
            }
            */

            if(returnCal.getTime().after(bufferCal.getTime()) || returnCal.getTime().before(checkoutCal.getTime()))
            {
                Toast.makeText(getBaseContext(), "Invalid return date: \nReturn date must be at most 7 days past checkout date.", Toast.LENGTH_LONG).show();
            }

            else if(checkoutCal.getTime().before(todayCal.getTime()))
            {
                Toast.makeText(getBaseContext(), "Invalid checkout date: \nYou cannot checkout a book earlier than today's date.", Toast.LENGTH_LONG).show();
            }




            /*
            else if(checkDate.before(todayDate))
            {
                Toast.makeText(getBaseContext(), "Invalid checkout date: \nYou cannot checkout a book earlier than today's date.", Toast.LENGTH_LONG).show();
                //next.setEnabled(false);
            }
            */

            else if(checkDateText.getText().toString().equals("Checkout Date") || returnDateText.getText().toString().equals("Return Date"))
            {
                Toast.makeText(this, "Please select both a Checkout and Return date.", Toast.LENGTH_LONG).show();
            }

            else
            {
                Boolean flag = false;
                Reservation test = new Reservation(checkoutCal.getTimeInMillis(), returnCal.getTimeInMillis());

                if(otterLibrary.availableBooks.size() == 0)
                    {
                        Intent i = new Intent(this, NoBookAvailable.class);
                        startActivity(i);
                    }

                else
                {
                    System.out.println("-------------------------------");
                    System.out.println("TODAY: " + todayCal.getTime());
                    System.out.println("CHECKOUT: " + checkoutCal.getTime());
                    System.out.println("RETURN: " + returnCal.getTime());
                    System.out.println("-------------------------------");
                    //next.setEnabled(true);

                    //Bundle the data to the next activity
                    Bundle dates = new Bundle();
                    dates.putLong("checkoutDate", checkoutCal.getTimeInMillis());
                    dates.putLong("returnDate", returnCal.getTimeInMillis());
                    dates.putInt("resNum", test.getReservationNumber());


                    Intent i = new Intent(this, BookSelectActivity.class);
                    i.putExtras(dates);
                    startActivity(i);
                }







            }

        }

    }
}
