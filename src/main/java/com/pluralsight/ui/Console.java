package com.pluralsight.ui;

import java.util.Scanner;

public class Console {

    public static final Scanner scanner = new Scanner(System.in);


    public static double askForDouble(String prompt) {
        while(true) {
            try {
                System.out.print(prompt);
                double result = scanner.nextDouble();
                scanner.nextLine();
                return result;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.printf("Your value is not valid. please try again\n");

            }
        }
    }

    public static String askForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }


    public static int askForInt(String prompt, int min, int max) {

        while(true) {
            try {
                System.out.print(prompt);
                int result = scanner.nextInt();

                if(result >= min && result <= max){
                    scanner.nextLine();
                    return result;
                } else {
                    scanner.nextLine();
                    System.out.printf("Your input was invalid. Please enter a number between %d and %d \n", min, max);
                }

            } catch (Exception e){
                System.out.printf("This must be a Integer value.\n");
                scanner.nextLine();
            }
        }
    }


    public static boolean askForBoolean(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String userInput = scanner.nextLine();
                return userInput.equalsIgnoreCase("YES");
            } catch (Exception e) {
                System.out.println("Your value must be a boolean.\n");
                scanner.nextLine();
            }
        }
    }

    public static boolean promptForExit(String prompt, String exitKey){
        String promptMsg = prompt + " (" + exitKey + " to exit. ):";
        String userExitInput = Console.askForString(promptMsg);
        if (userExitInput.equalsIgnoreCase(String.valueOf(exitKey))) {
            System.out.printf("Returning you to menu...");
            return true;

        } else {
            System.out.printf("\nDid you make a mistake? The exit key is %s\n", exitKey);
            scanner.nextLine();
        }
        return false;
    }

}