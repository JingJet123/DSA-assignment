/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import javax.swing.JOptionPane;

/**
 *
 * @author Lee Jing Jet
 */
public class DesignConsole {
    // Reset
    public static final String RESET = "\033[0m";  // Reset The Text

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    
    public void success(String msg) {
        System.out.println(GREEN + "\n<SUCCESS: " + msg + " >" + RESET);  //if success print green color msg
    }
    
    public void cancelled(String msg) {
        System.out.println(RED + "\n<CANCELLED: " + msg + " >" + RESET); //if cancelled print red color msg
    }
    
    public void failed(String msg) {
        System.out.println(RED + "\n<FAILED: " + msg + " >" + RESET); //if failed print red color msg
    }
    
    // Get error msg to purple color
    public void error(String msg) {
        System.out.println(RED + "<ERROR: " + msg + " >" + RESET); //if error print red color msg
    }

    // Get warning msg to purple color
    public void warning(String msg) {
        System.out.println(RED + "<WARNING: " + msg + " >" + RESET); //if warning print red color msg
    }
    
    // Get hint msg to blue color
    public void hint(String msg) {
        System.out.println(BLUE + "<HINT: " + msg + " >" + RESET); //if print hint print blue color msg
    }
    
    // Get other msg to blue color
    public void otherMsg(String msg, int nextLine) {
        for (int i = 0; i < nextLine; i++)
            System.out.println();                                             //other msg will be purple color
        System.out.println(PURPLE + "< " + msg + " >" + RESET);
    }
    
    // Get element to center
    public void toCenter(String title, int width) {
        int difference = width - title.length(), backWidth, frontWidth;
        
        frontWidth = backWidth = (difference - 2)/2;
        
        if (difference % 2 == 1)
            backWidth += 1;
        
        System.out.println("|" + getDivider(' ', frontWidth) + title + getDivider(' ', backWidth) + "|");

    }
    
    // Get table header's style
    public void tableHeader(String title, int tableWidth) {
        int difference = tableWidth - title.length(), backWidth, frontWidth;
        
        frontWidth = backWidth = (difference - 2)/2;
        
        if (difference % 2 == 1)
            backWidth += 1;
        
        System.out.println("\n\n"+PURPLE + getDivider('=', tableWidth));
        System.out.println(BLUE + "+" + getDivider(' ', frontWidth) + title + getDivider(' ', backWidth) + "+");
        System.out.println(PURPLE +getDivider('=', tableWidth));
    }
    
    // Get table divider line
    public void tableMiddleLine(int tableWidth) {
        System.out.println(getDivider('-', tableWidth));
    }
    
    // Get table bottom line
    public void tableFooter(int tableWidth) {
        System.out.println(getDivider('=', tableWidth));
    }
    
    // Get divident
    public static String getDivider(char symbol, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
    
    // Get Info Box
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
