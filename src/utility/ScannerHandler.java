/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import utility.DesignConsole;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Lee Jing Jet
 */
public class ScannerHandler {
    private final Scanner scanner = new Scanner(System.in);
    private DesignConsole print = new DesignConsole();

    public String nextLine(String input) {   
        System.out.print(input); // get string input
        return scanner.nextLine();
    }
    
    public char nextChar(String input) {    
        System.out.print(input); // get char input
        return scanner.nextLine().charAt(0);
    }
    
    public char nextChar(String input, String errorMessage, char... c) {
        boolean reenterInput;                          
        char confirm;                          //validation of char
        
        do {
            confirm = ScannerHandler.this.nextChar(input);
            boolean correct = false;            
            for (char ch : c) {
                if (confirm == ch) {
                    correct = true;
                }
            }
            
            if (correct)
                reenterInput = false;
            else {
                print.error(errorMessage); //print error message
                reenterInput = true;
            }
        } while(reenterInput);
        return confirm;
    }
    
    public boolean confirmation(String input) {  //validation of confirmation
        boolean reenterInput;                          
        char confirm;                          
        
        do {
            System.out.print(input);
            confirm = scanner.nextLine().charAt(0);
            if (confirm != 'Y' && confirm != 'y' && confirm != 'N' && confirm != 'n') {
                reenterInput = true;
                print.error("Your Input is Invalid. Please Enter Y/N :");  //if the input is invalid return error message
            } else
                reenterInput = false;
        } while(reenterInput);
        return confirm == 'Y' || confirm == 'y';
    }
    
    private void clear() {
        scanner.nextLine();
    }
    
    public double nextDouble(String input) {
        boolean reenterInput;                         //validation of double 
        double confirm = -1;                   
            
        do {
            
            System.out.print(input);
            try {
                reenterInput = false;
                confirm = scanner.nextDouble();
            } catch (InputMismatchException ex) {
                print.error("Invalid Input");    //if the input is invalid return error message
                reenterInput = true;
            } finally {
                clear();
            }
        } while(reenterInput);
        
        return confirm;
    }

    public double nextDouble(String input, String errorMessage, double min, double max) {
        boolean reenterInput;                          
        double confirm;                        
        
        do { 
            reenterInput = false;
            confirm = ScannerHandler.this.nextDouble(input);        //let user re enter valid double input
            if (confirm < min || confirm > max) {
                print.error(errorMessage);
                reenterInput = true;
            }
        } while(reenterInput);
        
        return confirm;
    }
    
    public int nextInt(String input) {
        boolean reenterInput;                          
        int confirm = -1;                     
                                                      //validation of integer
        do {                                          
            reenterInput = false;
            System.out.print(input);
            try {
                confirm = scanner.nextInt();
            } catch (InputMismatchException ex) {
                print.error("Invalid Input");       //if invalid ,print error message
                reenterInput = true;
            } finally {
                clear();
            }
        } while(reenterInput);
        return confirm;
    }

    public int nextInt(String input, String errorMessage, int min, int max) {
        boolean reenterInput;                          
        int confirm;                           
        
        do {
            confirm = ScannerHandler.this.nextInt(input);
            if (confirm < min || confirm > max) {
                print.error(errorMessage);                         //let user re enter integer input
                reenterInput = true;
            } else
                reenterInput = false;
        } while(reenterInput);
        
        return confirm;
    }
}
