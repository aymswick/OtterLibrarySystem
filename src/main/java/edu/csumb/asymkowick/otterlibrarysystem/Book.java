package edu.csumb.asymkowick.otterlibrarysystem;

/**
 * Title: Book.java
 * Abstract: This is the class for a Book object.  Contains Title, ISBN, Author, and Fee.
 * Author: Anthony Symkowick
 * ID: 3895
 * Created by asymkowick on 4/22/15.
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;


public class Book implements Parcelable {
    String title;
    String isbn;
    String author;
    double fee;
    boolean isReserved;

    public Book() {

    }



    public Book(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.isbn = in.readString();
        this.fee = in.readDouble();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getAuthor() {
        return this.author;
    }

    public double getFee() {
        return fee;
    }

    public String getFormattedFee() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formattedFee = formatter.format(fee);
        return formattedFee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(isbn);
        dest.writeDouble(fee);

    }

    public void setReserved(Boolean res) {
        this.isReserved = res;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    public boolean equals(Book other) {
        if(this.title.equals(other.getTitle()))
            return true;
        else return false;
    }
}
