package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Christine Dao>
 * <cd33279>
 * <>
 */



import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.io.*;
import java.util.List;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        loop: while(true) {
            System.out.print("critters > ");                    //The tests pass when this is removed
            String inputKey = kb.nextLine();                    //Get the whole line typed by the user
            String[] splitInput = inputKey.split(" ");    //Split the string into separate words
            int inputNum = 0;                                   //Used to convert String to Int

            if(inputKey.equals("\n")){                          //If there's a new line, skip the iteration and start another one
                continue;
            }

            switch (splitInput[0]) {
                case "quit":
                    if(splitInput.length > 1){                  //If the input has more than 1 word,
                        System.out.println("error processing: " + inputKey);    //Error
                        break;
                    }
                    Critter.clearWorld();                       //Else, clear the world and leave the loop
                    break loop;
                case "show":
                    if(splitInput.length > 1){                  //If the input has more than 1 word,
                        System.out.println("error processing: " + inputKey);    //Error
                        break;
                    }
                    Critter.displayWorld();                     //Else, display the world
                    break;
                case "step":
                    if(splitInput.length > 2){                  //If the input has more than 2 words,
                        System.out.println("error processing: " + inputKey);    //Error
                        break;
                    }
                    if(splitInput.length < 2){                  //If the input only has one word,
                        Critter.worldTimeStep();                //Run worldTimeStep
                    }else {                                     //If there are exactly 2 words,
                        try {
                            inputNum = Integer.parseInt(splitInput[1]); //Convert the 2nd String into an integer
                            for (int i = 0; i < inputNum; i++) {        //Run worldTimeStep as many times as inputNum
                                Critter.worldTimeStep();
                            }
                        }catch(NumberFormatException event){            //If the 2nd String couldn't be converted,
                            System.out.println("error processing: " + inputKey);    //Error
                        }
                    }
                    break;
                case "seed":
                    if(splitInput.length > 2){                         //If there are more than 2 words,
                        System.out.println("error processing: " + inputKey);    //Error
                        break;
                    }
                    try {
                        inputNum = Integer.parseInt(splitInput[1]); //Convert the 2nd String to an integer
                        Critter.setSeed(inputNum);
                    }catch(NumberFormatException event){            //If the 2nd String couldn't be converted,
                        System.out.println("error processing: " + inputKey);    //Error
                    }
                    break;
                case "make":
                    if(splitInput.length == 2){                 //If there are exactly 2 words,
                        try{
                            Critter.makeCritter(splitInput[1]); //Create a critter of type specified by the 2nd String
                        }catch(InvalidCritterException event){  //If the critter does not exist,
                            System.out.println("error processing: " + inputKey);   //Error
                        }
                    } else if(splitInput.length == 3){          //If there are exactly 3 words,
                        try{
                            inputNum = Integer.parseInt(splitInput[2]);    //Convert the 3rd String into an integer
                        }catch (NumberFormatException event){              //If the 3rd String couldn't be converted,
                            System.out.println("error processing: " + inputKey);    //Error
                        }

                        try{
                            for(int i = 0; i < inputNum; i++){             //If the critter type is valid,
                                Critter.makeCritter(splitInput[1]);        //Create critters as many times as inputNum
                            }
                        }catch(InvalidCritterException event){             //If the critter type is not valid,
                            System.out.println("error processing: " + inputKey);    //Error
                        }
                    } else {
                        System.out.println("error processing: " + inputKey);   //If there are neither 2 or 3 words, error
                    }
                    break;
                case "stats":
                    try {
                       Critter.getInstances(splitInput[1]);
                    }catch(InvalidCritterException event){
                        System.out.println("error processing: " + inputKey);    //If the critter type is invalid, error
                    }
                    break;
                default:
                    System.out.println("invalid command: " + splitInput[0]);    //If none of the commands match the input, error
                    break;
            }
        }


        // System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
}
