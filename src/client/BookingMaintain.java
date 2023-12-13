/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;
import adt.OrderClause;
import entity.Booking;
import entity.MenuEntity;
import utility.ScannerHandler;
import utility.DesignConsole;
import java.util.Iterator;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import static utility.DesignConsole.BLUE;
import static utility.DesignConsole.PURPLE;
import static utility.DesignConsole.RED;

/**
 *
 * @author Lee Jing Jet
 */
public class BookingMaintain {

    private ListInterface<Booking> bookList;
    private ListInterface<MenuEntity> MenuList;
    private final ScannerHandler scanner = new ScannerHandler();
    private final DesignConsole print = new DesignConsole();

    private int bookIDindex = 1011;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 97, SPECIFY_TABLE_WIDTH = 45,
            MODIFY_REPORT_TABLE_WIDTH = 55, SUMMARY_TABLE_WIDTH = 42;

    private String addNewBook = "";
    private String editBook = "";
    private String removeBook = "";

    public BookingMaintain(ListInterface<Booking> bookList, ListInterface<MenuEntity> MenuList) {
        this.bookList = bookList;
        this.MenuList = MenuList;

    }

    public void main() {
        int choice;

        do {
            print.tableHeader("Customer Booking", TABLE_WIDTH);
            System.out.println(BLUE + "| 1. Add Booking                       |");
            System.out.println(BLUE + "| 2. Remove Booking                    |");
            System.out.println(BLUE + "| 3. Edit Booking                      |");
            System.out.println(BLUE + "| 4. Search Booking                    |");
            System.out.println(BLUE + "| 5. Sort Booking By                   |");
            System.out.println(BLUE + "| 6. Display Booking Details           |");
            System.out.println(BLUE + "| 7. Display Booking List By Meals     |");
            System.out.println(BLUE + "| 8. Display Reports                   |");
            System.out.println(BLUE + "| 9. Exit                              |");
            System.out.println(PURPLE + "========================================");

            choice = scanner.nextInt(BLUE + "Please Enter Your Choice: ", "Please enter a number between 1 to 9", 1, 9);

            switch (choice) {
                case 1:
                    addBooking();
                    break;
                case 2:
                    removeBooking();
                    break;
                case 3:
                    editBooking();
                    break;
                case 4:
                    searchBooking();
                    break;
                case 5:
                    sortBookingBy();
                    break;
                case 6:
                    allrecord();
                    break;
                case 7:
                    showBookingList();
                    break;
                case 8:
                    showReports();
                    break;
                default: {
                }
                break;
            }
        } while (choice != 9);
    }

    public void showReports() {
        print.tableHeader("REPORT LIST", TABLE_WIDTH);
        System.out.println(BLUE + "1. Modify Report                       |");
        System.out.println(BLUE + "2. Summary Report                      |");
        System.out.println(BLUE + "3. Back To Previous Page               |");
        print.tableFooter(TABLE_WIDTH);

        int choice = scanner.nextInt(BLUE + "Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

        switch (choice) {
            case 1:
                ModifyReport();
                break;
            case 2:
                SummaryReport();
                break;
            default: {
            }
            break;

        }
    }

    public void ModifyReport() {
        print.tableHeader(" MODIFY REPORT", MODIFY_REPORT_TABLE_WIDTH);

        // what record have added today
        print.toCenter("Added Booking Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format(BLUE + "| %5s | %20s | %20s |", "ID", "NAME", "MEAL"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(addNewBook)) {
            print.toCenter("No Added Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else {
            System.out.println(addNewBook);
        }
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);

        // what record have edited today
        print.toCenter("Edited Booking Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format(BLUE + "| %5s | %20s | %20s |", "ID", "NAME", "MEAL"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(editBook)) {
            print.toCenter("No Edited Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else {
            System.out.println(editBook);
        }
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);

        // what record have been removed today
        print.toCenter("Removed Booking Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format(BLUE + "| %5s | %20s | %20s |", "ID", "NAME", "MEAL"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(removeBook)) {
            print.toCenter("No Removed Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else {
            System.out.println(removeBook);
        }
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);
    }

    public void SummaryReport() {
        Iterator<MenuEntity> menuItr = MenuList.getIterator();
        //overrall record information
        print.tableHeader("SUMMARY REPORT", SUMMARY_TABLE_WIDTH);
        print.toCenter(String.format("Total of Booking: %d", bookList.sizeOf()), SUMMARY_TABLE_WIDTH);
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);

        System.out.println(String.format(BLUE + "| %20s | %15s |", "Meal", "Amount"));
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);

        double totalAmount;

        while (menuItr.hasNext()) {
            MenuEntity m = menuItr.next();

            Iterator<Booking> bookItr = bookList.getIterator();

            totalAmount = 0;

            while (bookItr.hasNext()) {
                Booking bk = bookItr.next();

                if (m.getMenuName().equals(bk.getmeal())) {
                    totalAmount += bk.getAmount();
                }
            }
            System.out.println(String.format(BLUE + "| %20s | %15s |", m.getMenuName(), totalAmount));
        }
        print.tableFooter(SUMMARY_TABLE_WIDTH);
    }

    public void addBooking() {
        print.tableHeader("Add Booking", TABLE_WIDTH);
        print.otherMsg("New Booking Details", 1);

        // generate Booking ID
        String BookID = "B" + String.format("%4d", bookIDindex++);
        System.out.println(BLUE + "Booking ID             : " + BookID);

        // Input booker name
        String BookName = nameValidation(BLUE + "Enter Booker Name     : ");

        // input quantity
        int quantity = scanner.nextInt(BLUE + "Enter Quantity Of Meals You Need      : ", "The Maximum quantity of meals we can provide is 1000.Please Enter Your Quantity Again", 1, 1000);

        // input booker phone number
        String phoneNumber = phonenumberValidation(BLUE + "Enter Phone Number   : ");

        // Choose meals type
        print.otherMsg("Choose The Meals You Want", 0);
        displayMenuMeal();
        int mealChoose = scanner.nextInt(PURPLE + "Enter The Meals Your Want   : ", String.format("Please enter a number between 1 to %d", MenuList.sizeOf()), 1, MenuList.sizeOf());
        String MenuName = MenuList.get(mealChoose - 1).getMenuName();

        // count the amount ny using quantity and price
        MenuEntity m = MenuList.firstOrDefault(c -> c.getMenuName().equalsIgnoreCase(MenuName));
        double amount = m.getPrice() * quantity;

        // Get local date
        DateTimeFormatter dte = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();

        // view for data have been input for checking purpose
        print.tableHeader("New Booking Details", SPECIFY_TABLE_WIDTH);
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Book ID", BookID);
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Booker Name ", BookName);
        System.out.printf(BLUE + "| %-18s | %-20d |\n", "Quantity", quantity);
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Phone Number", phoneNumber);
        System.out.printf(BLUE + "| %-18s | RM %-17.2f |\n", "Amount (RM)", amount);
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Date", dte.format(now));
        print.tableFooter(SPECIFY_TABLE_WIDTH);

        // confirm want to add data or not
        print.otherMsg("Please Makesure All The Details Are Correct.", 1);
        if (scanner.confirmation(BLUE + "Please Confirm Do You Want To Add This Booking? (Y = yes / N = no) >> ")) {

            bookList.add(new Booking(BookID, BookName, quantity, phoneNumber, MenuName, amount, dte.format(now)));
            addNewBook += String.format(BLUE + "| %5s | %20s | %20s |\n", BookID, BookName, MenuName);

            print.success("This Booking Is Added Successful!");
        } else {
            print.cancelled("Your Booking Has Been Cancelled.");
        }
    }

    public void removeBooking() {
        // to makesure the record is empty
        if (bookList.isEmpty()) {
            print.failed("No Booking Record.");
        } else {
            print.tableHeader("Remove Booking", LIST_TABLE_WIDTH);
            displayList(bookList, MenuList);
            print.otherMsg(String.format("Total Booking Record : %d", bookList.sizeOf()), 0);

            // get the book id
            String BookID = scanner.nextLine(BLUE + "\nPlease Enter The Book ID To Remove: ");
            Booking bookRemove = bookList.firstOrDefault(r -> r.getBookID().equalsIgnoreCase(BookID));

            // find the same id in the record
            if (bookRemove == null) {
                print.failed("Book ID Not Found");
            } else {
                // Show the record that search by user
                searchDisplay(bookRemove, MenuList);

                if (scanner.confirmation(BLUE + "Sure want to remove booking above? (Y = yes / N = no) >> ")) {
                    bookList.remove(bookRemove);
                    removeBook += String.format(BLUE + "| %5s | %20s | %20s |\n", bookRemove.getBookID(), bookRemove.getBookName(), bookRemove.getmeal());
                    print.success("The Record You Choose Is Successful Been Deleted!");
                } else {
                    print.cancelled("The Remove Request Has Been Cancelled");
                }
            }
        }
    }

    public void editBooking() {
        if (bookList.isEmpty()) {
            print.failed("No Such Booking In The Record");
        } else {
            print.tableHeader("EDIT BOOKING", LIST_TABLE_WIDTH);
            displayList(bookList, MenuList);
            print.otherMsg(String.format("Total Number Of Booking Record: %d", bookList.sizeOf()), 0);

            String BookID = scanner.nextLine(BLUE + "Enter The Book ID To Edit: ");
            Booking editBooking = bookList.firstOrDefault(b -> b.getBookID().equalsIgnoreCase(BookID));

            if (editBooking == null) {
                print.failed("Booking ID not found");
            } else {
                // Input booking name
                String BookName = nameValidation(BLUE + "Enter New Booking Name      : ");
                int quantity = scanner.nextInt(BLUE + "Enter New Quantity       : ", "The Maximum quantity of meals we can provide is 1000.Please Enter Your Quantity Again", 1, 1000);
                String phoneNumber = phonenumberValidation(BLUE + "Enter New Phone Number    : ");

                print.otherMsg("Choose The Meals You Want", 0);
                displayMenuMeal();
                int mealChoose = scanner.nextInt(BLUE + "Enter The Meal You Want   : ", String.format(RED + "Please enter a number between 1 to %d", MenuList.sizeOf()), 1, MenuList.sizeOf());
                String MenuName = MenuList.get(mealChoose - 1).getMenuName();
                MenuEntity m = MenuList.firstOrDefault(n -> n.getMenuName().equalsIgnoreCase(MenuName));
                double amount = m.getPrice() * quantity;

                // confirm want to edit the record
                if (scanner.confirmation(BLUE + "Do you sure you want to edit this record? (Y = yes / N = no) >> ")) {
                    editBooking.setBookName(BookName);
                    editBooking.setquantity(quantity);
                    editBooking.setPhoneNumber(phoneNumber);
                    editBooking.setmeal(MenuName);
                    editBooking.setAmount(amount);
                    editBook += String.format("| %5s | %20s | %20s |\n", editBooking.getBookID(), BookName, amount);
                    print.success("The record you edit is successful been edited! ");
                } else {
                    print.cancelled("Your Edit Request Is Been Cancelled");
                }
            }
        }
    }

    public void searchBooking() {
        print.tableHeader("SEARCH BOOKING", TABLE_WIDTH);
        System.out.println(BLUE + "1. Search Book ID                      |");
        System.out.println(BLUE + "2. Search Booker Name                  |");
        System.out.println(BLUE + "3. Search Phone Number                 |");
        System.out.println(BLUE + "4. Back To Previous Page               |");  //which user want to search by
        print.tableFooter(TABLE_WIDTH);

        int choice = scanner.nextInt(PURPLE + "Please Enter Which You Want To Search By: ", "Please enter a number between 1 to 5", 1, 5);

        switch (choice) {
            case 1:
                searchBookingId();
                break;
            case 2:
                searchBookingName();
                break;
            case 3:
                searchBookingPhoneNumber();
                break;
            default: {
            }
            break;
        }
    }

    public void searchBookingId() {
        print.tableHeader("SEARCH BOOK ID", TABLE_WIDTH);
        String BookID = scanner.nextLine(BLUE + "Enter book id to search (e.g. B0001): ");

        Booking bk = bookList.firstOrDefault(b -> b.getBookID().equalsIgnoreCase(BookID));

        if (bk != null) {
            searchDisplay(bk, MenuList);
        } else {
            print.failed("Book ID not found");
        }
    }

    public void searchBookingName() {
        print.tableHeader("SEARCH BOOKER NAME", TABLE_WIDTH);
        System.out.println(BLUE + "1. Search Name Starts With");
        System.out.println(BLUE + "2. Search Name Ends With");
        System.out.println(BLUE + "3. Search Name Contains");
        System.out.println(BLUE + "4. Back To Search Main Page");
        print.tableFooter(TABLE_WIDTH);
        int choice = scanner.nextInt(BLUE + "Please Choose Which Ways You Want To Choose By: ", "Please enter a number between 1 to 4", 1, 4);

        if (choice != 4) {
            final String BookName = scanner.nextLine("Please Enter Name To Search: ").toUpperCase();
            ListInterface<Booking> bList = new DoublyLinkedList<>();

            switch (choice) {
                case 1:
                    bList = bookList.where(b -> b.getBookName().toUpperCase().startsWith(BookName));
                    break;
                case 2:
                    bList = bookList.where(b -> b.getBookName().toUpperCase().endsWith(BookName));
                    break;
                case 3:
                    bList = bookList.where(b -> b.getBookName().toUpperCase().contains(BookName));
                    break;
                default:
                    break;
            }

            if (bList.isEmpty()) {
                System.out.println(BLUE + "Booker Name not found");
            } else {
                searchDisplay(bList);
            }
        }
    }

    public void searchBookingPhoneNumber() {
        print.tableHeader("SEARCH BY PHONE NUMBER", TABLE_WIDTH);
        System.out.println(BLUE + "1. Phone Number starts with");
        System.out.println(BLUE + "2. Phone Number ends with");
        System.out.println(BLUE + "3. Phone Number contains");
        System.out.println(BLUE + "4. Back to Previous");
        int choice = scanner.nextInt(BLUE + "Please Choose Which Ways You Want To Choose By: ", "Please enter a number between 1 to 4", 1, 4);

        if (choice != 4) {
            final String BookphoneNumber = scanner.nextLine("Enter phone number to search: ").toUpperCase();
            ListInterface<Booking> bList = new DoublyLinkedList<>();

            switch (choice) {
                case 1:
                    bList = bookList.where(b -> b.getPhoneNumber().toUpperCase().startsWith(BookphoneNumber));
                    break;
                case 2:
                    bList = bookList.where(b -> b.getPhoneNumber().toUpperCase().endsWith(BookphoneNumber));
                    break;
                case 3:
                    bList = bookList.where(b -> b.getPhoneNumber().toUpperCase().contains(BookphoneNumber));
                    break;
                default:
                    break;
            }

            if (bList.isEmpty()) {
                print.failed("Booker phone number not found");
            } else {
                searchDisplay(bList);
            }
        }
    }

    public void sortBookingBy() {
        print.tableHeader("SORT BOOKING RECORD BY", TABLE_WIDTH);

        System.out.println(BLUE + "1. Sort By Book ID                     |");
        System.out.println(BLUE + "2. Sort By Booker Name                 |");
        System.out.println(BLUE + "3. Sort By Date                        |");
        System.out.println(BLUE + "4. Back To Previous Page               |");
        print.tableFooter(TABLE_WIDTH);

        int choice = scanner.nextInt("Please Choose Which You Want To Sort By: ", "Please enter a number between 1 to 6", 1, 6);

        if (choice != 4) {
            print.otherMsg("Sort By List", 1);
            System.out.println(BLUE + "1. Ascending Order");
            System.out.println(BLUE + "2. Descending Order");
            System.out.println(BLUE + "3. Back To Previous Page");

            int sequenceType = scanner.nextInt(BLUE + "Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

            if (sequenceType != 3) {
                switch (choice) {
                    case 1:
                        sortById(sequenceType);
                        break;
                    case 2:
                        sortByName(sequenceType);
                        break;
                    case 3:
                        sortByDate(sequenceType);
                        break;
                    default: {
                    }
                    break;
                }
                print.success("Sort successfully, press 6 or 7 to view booking record");
            }
        }
    }

    public void sortById(int sequenceType) {
        switch (sequenceType) {
            case 1:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = bk1.getBookID().compareToIgnoreCase(bk2.getBookID());

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            case 2:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = bk1.getBookID().compareToIgnoreCase(bk2.getBookID());

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            default: {
            }
            break;
        }
    }

    public void sortByName(int sequenceType) {
        switch (sequenceType) {
            case 1:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = bk1.getBookName().compareToIgnoreCase(bk2.getBookName());

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            case 2:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = bk1.getBookName().compareToIgnoreCase(bk2.getBookName());

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            default: {

            }
        }
    }

    public void sortByDate(int sequenceType) {
        SimpleDateFormat dte = new SimpleDateFormat("dd-MM-yyyy");

        switch (sequenceType) {
            case 1:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = 0;
                    try {
                        sortval = dte.parse(bk1.getDate()).compareTo(dte.parse(bk2.getDate()));
                    } catch (ParseException e) {
                    }

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            case 2:
                bookList.orderBy((Booking bk1, Booking bk2) -> {
                    int sortval = 0;
                    try {
                        sortval = dte.parse(bk1.getDate()).compareTo(dte.parse(bk2.getDate()));
                    } catch (ParseException e) {
                    }

                    if (sortval == 0) {
                        return bk1.getBookName().compareToIgnoreCase(bk2.getBookName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return sortval > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
                break;
            default: {
            }
            break;
        }
    }

    public void searchDisplay(Booking book, ListInterface<MenuEntity> MenuList) {                     // Search for 1 result
        Iterator<MenuEntity> menuItr = MenuList.getIterator();

        while (menuItr.hasNext()) {
            MenuEntity m = menuItr.next();

            if (book.getmeal().compareToIgnoreCase(m.getMenuName()) == 0) {
            }
        }

        print.tableHeader("DONOR SEARCH RESULT", SPECIFY_TABLE_WIDTH);
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Book ID", book.getBookID());
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Book Name ", book.getBookName());
        System.out.printf(BLUE + "| %-18s | %-20d |\n", "Quantity", book.getquantity());
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Phone Number", String.valueOf(book.getPhoneNumber()).replaceFirst("(\\d{1})(\\d{2})(\\d{3})(\\d+)", "(+6$1)$2-$3 $4"));
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Donation Category", book.getmeal());
        System.out.printf(BLUE + "| %-18s | %-20.2f |\n", "Amount", book.getAmount());
        System.out.printf(BLUE + "| %-18s | %-20s |\n", "Date", book.getDate());
        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }

    public void searchDisplay(ListInterface<Booking> bookList) {      // Search for more than 1 results
        print.tableHeader("BOOKING SEARCH RESULT", LIST_TABLE_WIDTH);
        displayList(bookList, MenuList);
    }

    public void showBookingList() {
        Iterator<MenuEntity> menuItr = MenuList.getIterator();
        print.tableHeader("DISPLAY Booking Record", TABLE_WIDTH);

        int backNum = 1;
        for (int i = 0; menuItr.hasNext(); i++) {
            System.out.println(String.format(BLUE + "%d. Only Display %s Meals", (i + 1), menuItr.next().getMenuName()));
            backNum++;
        }

        System.out.printf(BLUE + "%d. Back To Previous Main Page\n", backNum);
        print.tableFooter(TABLE_WIDTH);

        int choice = scanner.nextInt(PURPLE + "Enter Your Choice: ", String.format("Please enter a number between 1 to %d", backNum), 1, backNum);

        if (choice != backNum) {
            int numOf = 0;
            // Get category name 
            String menuName = MenuList.get(choice - 1).getMenuName();

            print.tableHeader(String.format("Booking Details (%s)", menuName.toUpperCase()), LIST_TABLE_WIDTH);

            Iterator<Booking> bookItr = bookList.getIterator();

            System.out.printf(BLUE + "| %-5s | %-20s | %-5s | %-20s | %-15s | %-13s |\n", "ID", "NAME", "AGE", "PHONE NUMBER", "AMOUNT", "DATE");
            print.tableMiddleLine(LIST_TABLE_WIDTH);
            while (bookItr.hasNext()) {
                Booking bk = bookItr.next();
                if (menuName.compareToIgnoreCase(bk.getmeal()) == 0) {
                    System.out.println(bk);
                    numOf++;
                }
            }

            if (numOf == 0) {
                print.toCenter(String.format("No Record Found In %s Meal", menuName), LIST_TABLE_WIDTH);
            }

            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of %s Meals: %d", menuName, numOf), 0);
        }
    }

    // Display all booking record
    public void allrecord() {
        print.tableHeader("ALL BOOK RECORD", LIST_TABLE_WIDTH);
        displayList(bookList, MenuList);
        print.otherMsg(String.format("Total Number Of Booking: %d", bookList.sizeOf()), 0);
    }

    // Display booking record by meal
    public void displayList(ListInterface<Booking> bookList, ListInterface<MenuEntity> MenuList) {
        Iterator<MenuEntity> menuItr = MenuList.getIterator();
        int numOf;

        while (menuItr.hasNext()) {
            MenuEntity m = menuItr.next();
            print.toCenter(RED + m.getMenuName().toUpperCase(), 104);
            print.tableMiddleLine(LIST_TABLE_WIDTH);

            System.out.printf(BLUE + "| %-5s | %-20s | %-5s | %-20s | %-15s | %-10s |\n", "ID", "NAME", "QUANTITY", "PHONE NUMBER", "AMOUNT", "DATE");
            print.tableMiddleLine(LIST_TABLE_WIDTH);

            Iterator<Booking> bookItr = bookList.getIterator();

            numOf = 0;

            while (bookItr.hasNext()) {
                Booking bk = bookItr.next();
                if (bk.getmeal().compareToIgnoreCase(m.getMenuName()) == 0) {
                    System.out.println(bk);
                    numOf++;
                }
            }

            if (numOf == 0) {
                print.toCenter("No record for this meal!", LIST_TABLE_WIDTH);
            }
            print.tableFooter(LIST_TABLE_WIDTH);
        }
    }

    public void displayMenuMeal() {
        Iterator<MenuEntity> menuItr = MenuList.getIterator();

        for (int i = 0; menuItr.hasNext(); i++) {
            System.out.println(String.format(PURPLE + "%d. %s", (i + 1), menuItr.next().getMenuName()));
        }
    }

    // name sortvalidation
    public String nameValidation(String promptInfo) {
        boolean sortvalidname = false, inputname = true;
        String mainErrorName = "Please enter a sortvalid Booker name (e.g. Jonas)";
        int numChar = 0;
        String BookName;

        do {
            BookName = scanner.nextLine(promptInfo);

            inputname = true;
            for (int i = 0; i < BookName.length(); i++) {
                if (Character.isLetter(BookName.charAt(i)) == false && BookName.charAt(i) != ' ') {
                    inputname = false;                          //to makesure the input name only in char format
                } else if (Character.isLetter(BookName.charAt(i))) {
                    numChar++;
                }
            }

            if (!inputname) {                                     // if the input name is insortvalid will print error message
                print.error(mainErrorName);
                print.error("The Booker Name Is Insortvalid Because Contain Special Symbols Or Numbers");
            } else if (BookName.length() <= 0 || numChar == 0) {
                print.error(mainErrorName);
                print.error("The Booker Name Cannot Be Empty!");
            } else if (BookName.length() >= 20) {
                print.error(mainErrorName);
                print.error("The Booker Name Must Less Than 20 Characters For Record Purpose! ");
            } else {
                sortvalidname = true;
            }

        } while (!sortvalidname);

        return BookName;
    }

    public String phonenumberValidation(String promptInfo) {
        boolean sortvalidphone = false;
        String mainErrorPhoneNumber = "Please enter a sortvalid phone number (e.g. 0129431228)";
        String phoneNumber;

        do {
            phoneNumber = scanner.nextLine(promptInfo);

            if (!phoneNumber.matches("[0-9]+")) {
                print.error(mainErrorPhoneNumber);
                print.error("The Phone Number Cannot Contain Characters Or Special Symbols");
            } else if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '1') {
                print.error(mainErrorPhoneNumber);
                print.error("The Phone Number Format Must Start With 01!");
            } else if (phoneNumber.length() < 10) {
                print.error(mainErrorPhoneNumber);
                print.error("The Phone Number Cannot Less Than 10 Digits!");
            } else if (phoneNumber.length() > 11) {
                print.error(mainErrorPhoneNumber);
                print.error("The phone number cannot more than 11 Digits!");
            } else {
                sortvalidphone = true;
            }
        } while (!sortvalidphone);

        return phoneNumber;
    }
}
