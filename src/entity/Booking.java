/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import adt.*;
import utility.DesignConsole;
/**
 *
 * @author Lee Jing Jet
 */
public class Booking {
    private String BookID;
    private String BookName;
    private int quantity;
    private String phoneNumber;
    private String meal;
    private double amount;
    private String date;
    
    public Booking(String BookID, String BookName, int quantity, String phoneNumber, String meal, double amount,String date) {
        this.BookID = BookID;
        this.BookName = BookName;
        this.quantity = quantity;
        this.phoneNumber = phoneNumber;
        this.meal = meal;
        this.amount = amount;
        this.date = date;
    }
    
    public String getBookID() {
        return BookID;
    }
    
    public String getBookName() {
        return BookName;
    }
    
    public int getquantity() {
        return quantity;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getmeal() {
        return meal;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setBookName(String BookName) {
        this.BookName = BookName;
    }
    
    public void setquantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    public void setmeal(String meal) {
        this.meal = meal;
    }
    
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void setDate(String date) {
        this.date = date;
    } 
    
    @Override
    public String toString() {
            return "| " + String.format(DesignConsole.BLUE +"%-5s", BookID) 
                + " | " + String.format(DesignConsole.BLUE +"%-20s", BookName) 
                + " | " + String.format(DesignConsole.BLUE +"%-8d", quantity)
                + " | " + String.format(DesignConsole.BLUE +"%-20s", String.valueOf(phoneNumber).replaceFirst("(\\d{1})(\\d{2})(\\d{3})(\\d+)", "(+6$1)$2-$3 $4"))
                + " | " + String.format(DesignConsole.BLUE +"%-15.2f", amount)
                + " | " + String.format(DesignConsole.BLUE +"%-10s", date) + " |";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Booking other = (Booking) obj;
        if (!Objects.equals(this.BookID, other.BookID))
            return false;
        return true;
    }

    

    
    
}
