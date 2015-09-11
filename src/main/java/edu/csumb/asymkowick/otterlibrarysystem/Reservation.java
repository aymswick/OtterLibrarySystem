package edu.csumb.asymkowick.otterlibrarysystem;

import java.util.Date;

/**
 * Created by asymkowick on 5/9/15.
 */
public class Reservation {

    private Customer reservedBy;
    private Book reservedBook;
    private Long checkoutDate;
    private Long returnDate;
    private static int currentResNum = 1;
    private int reservationNumber;


    public Reservation(Customer c, Book b, Long dCheck, Long dReturn) {
        this.reservedBy = c;
        this.reservedBook = b;
        this.checkoutDate = dCheck;
        this.returnDate = dReturn;
        this.reservationNumber = currentResNum++;
    }

    public Reservation(Long checkDate, Long returnDate) {
        this.checkoutDate = checkDate;
        this.returnDate = returnDate;
    }

    public Customer getReservedBy() {
        return this.reservedBy;
    }

    public Book getReservedBook() {
        return this.reservedBook;
    }

    public Long getCheckoutDate() {
        return this.checkoutDate;
    }

    public Long getReturnDate() {
        return this.returnDate;
    }

    public int getReservationNumber() {
        return this.reservationNumber;
    }

    public String toString() {
        return reservedBook.getTitle() + ": " + checkoutDate + " until " + returnDate;
    }


    public boolean isDuring(Reservation r) {

        Date check = new Date(this.checkoutDate);
        Date returnD = new Date(r.returnDate);


        if(this.getReservedBook().getTitle().equals(r.getReservedBook().getTitle()))
        {
            if (check.before(returnD))
            {
                System.out.println("this.checkoutDate > r.returnDate");
                return true;
            }

            else return false;
        }

        else return false;
    }




}
