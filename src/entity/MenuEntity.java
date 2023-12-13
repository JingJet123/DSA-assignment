/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import adt.*;
import static utility.DesignConsole.BLUE;
/**
 *
 * @author Lee Jing Jet
 */
public class MenuEntity {
    private String MenuID;
    private String MenuName;
    private double Price;
    
    private ListInterface<Booking> bookingList = new DoublyLinkedList<>();
    
    public MenuEntity(String MenuID, String MenuName, double Price) {
        this.MenuID = MenuID;
        this.MenuName = MenuName;
        this.Price = Price;
    }
    
    public String getMenuID() {
        return MenuID;
    }
    
    public String getMenuName() {
        return MenuName;
    }
    
    public double getPrice() {
        return Price;
    }
    
    public void setMenuID(String MenuID) {
        this.MenuID = MenuID;
    }
    
    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }
    
    public void setPrice(double Price) {
        this.Price = Price;
    }
      
    
    public ListInterface<Booking> getbookingList() {
        return bookingList;
    }
    
    public void setbookingList(ListInterface<Booking> bookingList) {
        this.bookingList = bookingList;
    }
    
    @Override
    public String toString() {
        return "| " + String.format(BLUE+"%-5s", MenuID)
                + " | " + String.format(BLUE+"%-20s", MenuName)
                + " | " + String.format(BLUE+"%-25.2f", Price)
                 + " |";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final MenuEntity other = (MenuEntity) obj;
        if (!Objects.equals(this.MenuID, other.MenuID))
            return false;
        return true;
    }
    
    public void addBooking(Booking booking) {
        bookingList.add(booking);
    }
    
    public void removeBooking(Booking booking) {
        bookingList.remove(booking);
    }
    
    public Booking getBooking(String BookID) {
        for (int i = 0; i < bookingList.sizeOf(); i++) {
            if (bookingList.get(i).getBookID().equals(bookingList))
                return bookingList.get(i);
        }
        return null;
    }
}
