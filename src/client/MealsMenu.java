/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;
import adt.OrderClause;
import entity.MenuEntity;
import utility.DesignConsole;
import utility.ScannerHandler;
import java.util.Iterator;
import static utility.DesignConsole.BLUE;
import static utility.DesignConsole.PURPLE;

/**
 *
 * @author Lee Jing Jet
 */
public class MealsMenu {

    private ListInterface<MenuEntity> MenuList;
    private final ScannerHandler scanner = new ScannerHandler();
    private final DesignConsole print = new DesignConsole();

    private int menuIndex = 1004;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 60, SPECIFY_TABLE_WIDTH = 50;

    public MealsMenu(ListInterface<MenuEntity> MenuList) {
        this.MenuList = MenuList;
    }

    public void main() {
        int choice;

        do {
            print.tableHeader("Meals Menu", TABLE_WIDTH);
            System.out.println(BLUE + "| 1. View Menu                         |");
            System.out.println(BLUE + "| 2. Add Menu                          |");
            System.out.println(BLUE + "| 3. Remove Menu                       |");
            System.out.println(BLUE + "| 4. Edit Menu                         |");
            System.out.println(BLUE + "| 5. Search Menu                       |");
            System.out.println(BLUE + "| 6. Sort Menu By                      |");
            System.out.println(BLUE + "| 7. Exit                              |");
            System.out.println(PURPLE + "========================================");

            choice = scanner.nextInt(BLUE + "Enter Your Choice: ", "Please enter a number between 1 to 7", 1, 7);

            switch (choice) {
                case 1:
                    showMenuRecord();
                    break;
                case 2:
                    addMeal();
                    break;
                case 3:
                    removeMeal();
                    break;
                case 4:
                    editMenu();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 6:
                    sortByList();
                    break;
                default: {
                }
                break;
            }

        } while (choice != 7);
    }

    public void addMeal() {
        print.tableHeader("ADD MEAL", TABLE_WIDTH);
        print.otherMsg("New MENU MEAL", 1);

        // generate menu id
        String MenuID = "M" + String.format("%4d", menuIndex++);
        System.out.println(BLUE +"Category ID         : " + MenuID);

        // input Menu Name
        String MenuName = scanner.nextLine(BLUE + "Enter Menu Name : ");
        
        // input price
        Double price = scanner.nextDouble(BLUE +"Enter Price: ");
        

        // Show the details
        print.tableHeader("New Category Details", SPECIFY_TABLE_WIDTH);
        System.out.printf(BLUE +"| %-18s | %-25s |\n", "Menu ID", MenuID);
        System.out.printf(BLUE +"| %-18s | %-25s |\n", "Menu Name", MenuName);
        System.out.printf(BLUE +"| %-18s | %-25.2f |\n", "Price", price);
        print.tableFooter(SPECIFY_TABLE_WIDTH);

        // Confirmation
        print.otherMsg("Please make sure that all the details given above is correct", 1);
        print.hint("The above details can be edited in the future");

        if (scanner.confirmation("Do You Sure You Want To Add This Meals (Y = yes / N = no)? >> ")) {
            MenuList.add(new MenuEntity(MenuID, MenuName, price));
            print.success("The Meal Is Successful Added");
        } else {
            print.cancelled("Your Add Request Has Been Cancelled");
        }
    }

    public void removeMeal() {
        // Check whether is empty
        if (MenuList.isEmpty()) {
            print.failed("No Meal Record!");
        } else {
            // Show all meal record
            print.tableHeader("REMOVE MENU", LIST_TABLE_WIDTH);
            displayMenu(MenuList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Meals: %d", MenuList.sizeOf()), 0);

            // Get the Menu id
            String MenuID = scanner.nextLine(BLUE +"\nEnter The Menu ID To Remove (e.g. M1001): ");
            MenuEntity removeMenu = MenuList.firstOrDefault(d -> d.getMenuID().equalsIgnoreCase(MenuID));

            // Compare the id with record
            if (removeMenu == null) {
                print.failed("Menu ID Not Found");
            } else {
                // Show user search result
                displaySearchMenu(BLUE+"Menu to remove", removeMenu);

                if (scanner.confirmation(BLUE+"Are You Sure You Want To Remove This Meal? (Y = yes / N = no) >> ")) {
                    MenuList.remove(removeMenu);
                    print.success("The Menu You Choose Has Been Removed Successful");
                } else {
                    print.cancelled("Your Remove Request Has Been Cancelled");
                }
            }
        }
    }

    public void editMenu() {
        // Check whether is empty
        if (MenuList.isEmpty()) {
            print.failed("No Menu Record!");
        } else {
            // Show all Menu Record
            print.tableHeader("EDIT MENU", LIST_TABLE_WIDTH);
            displayMenu(MenuList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Meal: %d", MenuList.sizeOf()), 0);

            // Input menu id
            String MenuID = scanner.nextLine(BLUE+"\nEnter The Menu ID To Edit (e.g. M1001): ");
            MenuEntity editMenu = MenuList.firstOrDefault(m -> m.getMenuID().equalsIgnoreCase(MenuID));

            // Get the category id
            if (editMenu == null) {
                print.failed("Menu ID Not Found");
            } else {
                displaySearchMenu(BLUE +"Menu to edit", editMenu);
                // Enter new meal's name
                String MenuName = scanner.nextLine("Enter New Menu Name      : ");
                double Price = scanner.nextDouble("Enter New Menu Price      : ");

                if (scanner.confirmation(BLUE+"Do Your Sure You Want To Edit This Menu? (Y = yes / N = no) >> ")) {
                    // Set the details into the list
                    editMenu.setMenuName(MenuName);
                    editMenu.setPrice(Price);

                    print.success("Your Record Is Record Successful");
                } else {
                    print.cancelled("Your Edit Request Has Been Cancelled");
                }
            }
        }
    }

    public void searchMenu() {
        // Choose search by
        print.tableHeader("SEARCH MENU", TABLE_WIDTH);
        System.out.println(BLUE+"1. Search By Category ID");
        System.out.println(BLUE+"2. Search By Category Name");
        System.out.println(BLUE+"3. Back To Donation Category Main Page");

        int choice = scanner.nextInt(BLUE+"Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

        switch (choice) {
            case 1:
                searchMenuId();
                break;
            case 2:
                searchMenuName();
                break;
            default: {
            }
            break;
        }
    }

    public void searchMenuId() {
        print.tableHeader("SEARCH MENU ID", TABLE_WIDTH);
        String MenuID = scanner.nextLine(BLUE+"Enter menu id to search (e.g. M1001): ");

        // Check id
        MenuEntity me = MenuList.firstOrDefault(m -> m.getMenuID().equalsIgnoreCase(MenuID));

        // Show result
        if (me != null) {
            displaySearchMenu(BLUE+"Menu search result", me);
        } else {
            print.failed("Menu ID not found");
        }
    }

    public void searchMenuName() {
        print.tableHeader("SEARCH MENU NAME", TABLE_WIDTH);
        System.out.println(BLUE+"1. Search Name Starts With");
        System.out.println(BLUE+"2. Search Name Ends With");
        System.out.println(BLUE+"3. Search Name Contains");
        System.out.println(BLUE+"4. Back To Search Main Page");
        int choice = scanner.nextInt(BLUE+"Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);

        if (choice != 4) {
            String MenuName = scanner.nextLine(BLUE+"Enter name to search: ").toUpperCase();
            ListInterface<MenuEntity> mList = new DoublyLinkedList<>();

            // Get all menu which related with the input
            switch (choice) {
                case 1:
                    mList = MenuList.where(m -> m.getMenuName().toUpperCase().startsWith(MenuName));
                    break;
                case 2:
                    mList = MenuList.where(m -> m.getMenuName().toUpperCase().endsWith(MenuName));
                    break;
                case 3:
                    mList = MenuList.where(d -> d.getMenuName().toUpperCase().contains(MenuName));
                    break;
                default:
                    break;
            }

            // Show result
            if (mList.isEmpty()) {
                print.failed("Menu name not found");
            } else {
                displaySearchMenu(mList);
            }
        }
    }


    public void sortByList() {
        print.tableHeader("SORT MENU LIST BY", TABLE_WIDTH);

        System.out.println(BLUE+"1. Sort By Menu ID");
        System.out.println(BLUE+"2. Sort By Menu Name");
        System.out.println(BLUE+"3. Back To Previous Page");

        int choice = scanner.nextInt(BLUE+"Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);

        if (choice != 3) {
            print.otherMsg("Sort Menu By", 1);
            System.out.println(BLUE+"1. Ascending Order");
            System.out.println(BLUE+"2. Descending Order");
            System.out.println(BLUE+"3. Back To Previous Page");

            int sequenceType = scanner.nextInt(BLUE+"Enter Your Choice: ", "Please enter a number between 1 to 2", 1, 2);

            if (sequenceType != 3) {
                switch (choice) {
                    case 1:
                        sortById(sequenceType);
                        break;
                    case 2:
                        sortByName(sequenceType);
                        break;
                    default: {
                    }
                    break;
                }
                print.success("Sort successfully, press 6 to view menu record");
            }
        }
    }

    public void sortById(int sequenceType) {
        switch (sequenceType) {
            case 1:
                MenuList.orderBy((m1, m2)
                        -> m1.getMenuID().compareToIgnoreCase(m2.getMenuID()) < 0
                        ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                break;
            case 2:
                MenuList.orderBy((m1, m2)
                        -> m1.getMenuID().compareToIgnoreCase(m2.getMenuID()) > 0
                        ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                break;
            default: {
            }
            break;
        }
    }

    public void sortByName(int sequenceType) {
        switch (sequenceType) {
            case 1:
                MenuList.orderBy((m1, m2)
                        -> m1.getMenuName().compareToIgnoreCase(m2.getMenuName()) < 0
                        ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                break;
            case 2:
                MenuList.orderBy((m1, m2)
                        -> m1.getMenuName().compareToIgnoreCase(m2.getMenuName()) > 0
                        ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
                break;
            default: {
            }
            break;
        }
    }


    public void showMenuRecord() {
        print.tableHeader("MENU", LIST_TABLE_WIDTH);
        displayMenu(MenuList);
        print.tableFooter(LIST_TABLE_WIDTH);
        print.otherMsg(String.format("Total Number Of Menu: %d", MenuList.sizeOf()), 0);
    }

    public void displaySearchMenu(String title, MenuEntity menu) {
        print.tableHeader(title.toUpperCase(), SPECIFY_TABLE_WIDTH);
        System.out.printf(BLUE+"| %-18s | %-25s |\n", "Category ID", menu.getMenuID());
        System.out.printf(BLUE+"| %-18s | %-25s |\n", "Category Name", menu.getMenuName());
        System.out.printf(BLUE+"| %-18s | %-25.2f |\n", "Price", menu.getPrice());
        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }

    public void displaySearchMenu(ListInterface<MenuEntity> menu) {
        print.tableHeader("MENU SEARCH RESULT", LIST_TABLE_WIDTH);
        displayMenu(menu);
        print.tableFooter(LIST_TABLE_WIDTH);
    }

    public void displayMenu(ListInterface<MenuEntity> MenuList) {
        Iterator<MenuEntity> itr = MenuList.getIterator();

        System.out.printf(BLUE+"| %-5s | %-20s | %-25s |\n", "ID", "NAME", "PRICE");
        print.tableMiddleLine(LIST_TABLE_WIDTH);

        while (itr.hasNext()) {
            MenuEntity me = itr.next();
            System.out.println(me.toString());
        }
    }
}
