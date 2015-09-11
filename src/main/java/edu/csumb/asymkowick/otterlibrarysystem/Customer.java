package edu.csumb.asymkowick.otterlibrarysystem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asymkowick on 4/22/15.
 */
public class Customer implements Parcelable {

    String username;
    String password;

    public Customer() {

        username = "Generic";
        password = "nothing";
    }

    public Customer(Parcel in) {
        username = in.readString();
        password = in.readString();
    }



    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }



    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Customer) {

            Customer c = (Customer) obj;


            if (this.username.equals(c.getUsername()) && this.password.equals(c.getPassword()))
                return true;
            else return false;
        }

        else return false;
    }


    @Override
    public String toString() {
        return "Username: " + username + "\n" +
                "Password: " + password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };


}
