/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.UI;

import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author William
 */

@Component
public class UserIOImpl implements UserIO {

    Scanner myScanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return myScanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(this.readString(prompt)); //prints out the prompt using, 
                //then tries to  verify if the user's input is valid via ReadString
            } catch (NumberFormatException e) {
                this.print("That wasn't a valid input, try again.");
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int num;
        do {
            System.out.println(prompt);
            num = myScanner.nextInt();
        } while (num < min || num > max);
        return num;
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(prompt)); //prints out the prompt using, 
                //then tries to  verify if the user's input is valid via ReadString
            } catch (NumberFormatException e) {
                this.print("That wasn't a valid input, try again.");
            }
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double num;
        do {
            System.out.println(prompt);
            num = myScanner.nextDouble();
        } while (num < min || num > max);
        return num;
    }

    @Override
    public float readFloat(String prompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(prompt)); //prints out the prompt using, 
                //then tries to  verify if the user's input is valid via ReadString
            } catch (NumberFormatException e) {
                this.print("That wasn't a valid input, try again.");
            }
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float num;
        do {
            System.out.println(prompt);
            num = myScanner.nextFloat();
        } while (num < min || num > max);
        return num;
    }

    @Override
    public long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(prompt)); //prints out the prompt using, 
                //then tries to  verify if the user's input is valid via ReadString
            } catch (NumberFormatException e) {
                this.print("That wasn't a valid input, try again.");
            }
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long num;
        do {
            System.out.println(prompt);
            num = myScanner.nextLong();
        } while (num < min || num > max);
        return num;
    }

}
