/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;
import entity.Booking;
import entity.MenuEntity;
import utility.ScannerHandler;
import utility.DesignConsole;
import static utility.DesignConsole.BLUE;
import static utility.DesignConsole.PURPLE;

/**
 *
 * @author Lee Jing Jet
 */
public class Menu {

    /**
     * @param args the command line arguments
     */
    private ListInterface<Booking> bookList;
    private ListInterface<MenuEntity> MenuList;
    private BookingMaintain bookRecord;
    private MealsMenu MealsRecord;

    private final ScannerHandler scanner = new ScannerHandler();
    private final DesignConsole print = new DesignConsole();

    public Menu() {
        bookList = new DoublyLinkedList();
        addBookingDetails();

        MenuList = new DoublyLinkedList();
        addMenuList();

        bookRecord = new BookingMaintain(bookList, MenuList);
        MealsRecord = new MealsMenu(MenuList);
    }

    public void addBookingDetails() {
        Booking d1 = new Booking("B1001", "Jet", 50, "0127835566", "Fried Rice", 600, "01-01-2022");
        Booking d2 = new Booking("B1002", "Jackson", 100, "0162049399", "Fried Noodles", 1000, "12-03-2022");
        Booking d3 = new Booking("B1003", "Shanice", 25, "0178832213", "Fried Noodles", 250, "25-03-2022");
        Booking d4 = new Booking("B1004", "Louis", 30, "0199431228", "Curry Chicken", 600, "21-05-2022");
        Booking d5 = new Booking("B1005", "Miko", 10, "0112116711", "Curry Chicken", 200, "18-06-2022");

        bookList.addAll(d1, d2, d3, d4, d5);
    }

    public void addMenuList() {
            MenuList.add(new MenuEntity("M1001", "Fried Rice", 12.00));
        MenuList.add(new MenuEntity("M1002", "Fried Noodles", 10.00));
        MenuList.add(new MenuEntity("M1003", "Curry Chicken", 20.00));
    }

    public void run() {
        int choice;
        do {
            print.tableHeader("Catering Services System", 40);
            System.out.println(BLUE + "| 1. Meals Menu                        |");
            System.out.println(BLUE + "| 2. Customer Booking                  |");
            System.out.println(BLUE + "| 3. Exit                              |");
            System.out.println(PURPLE + "========================================");
            choice = scanner.nextInt(BLUE + "Please Enter Your Choice: ", "Your choice is Invalid Pls Re-enter", 1, 3);

            switch (choice) {
                case 1:
                    MealsRecord.main();
                    break;
                case 2:
                    bookRecord.main();
                    break;
                default: {
                }
                break;
            }

        } while (choice != 3);

        print.otherMsg("Thank you. See You Next Time!", 1);
    }

    public static void main(String[] args) {
        new Menu().run();
    }

    public static String getDivider(char lineTable, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(lineTable);
        }
        return stringBuilder.toString();
    }
}
