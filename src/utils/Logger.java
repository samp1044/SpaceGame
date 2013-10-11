/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import support.Settings;

/**
 *
 * @author Sami
 */
public class Logger {
    private Class c;
    
    public Logger(Class c) {
        this.c = c;
    }
    
    public void run(String message) {
        if (Settings.RUN) {
            String output = "";

            output += c.getName();
            output = makeStringToGivenLength(output,30);

            output += new Time().toString();
            output = makeStringToGivenLength(output,39);

            output += "RUN: ";
            output = makeStringToGivenLength(output,46);
            output += message;

            print(output);
        }
    }
    
    public void debug(String message) {
        if (Settings.DEBUG) {
            String output = "";

            output += c.getName();
            output = makeStringToGivenLength(output,30);

            output += new Time().toString();
            output = makeStringToGivenLength(output,39);

            output += "DEBUG: ";
            output = makeStringToGivenLength(output,46);
            output += message;

            print(output);
        }
    }
    
    public void info(String message) {
        if (Settings.INFO) {
            String output = "";

            output += c.getName();
            output = makeStringToGivenLength(output,30);

            output += new Time().toString();
            output = makeStringToGivenLength(output,39);

            output += "INFO: ";
            output = makeStringToGivenLength(output,46);
            output += message;

            print(output);
        }
    }
    
    public  void error(String message) {
        if (Settings.ERROR) {
            String output = "";

            output += c.getName();
            output = makeStringToGivenLength(output,30);

            output += new Time().toString();
            output = makeStringToGivenLength(output,39);

            output += "ERROR: ";
            output = makeStringToGivenLength(output,46);
            output += message;

            print(output);
        }
    }
    
    private String makeStringToGivenLength(String s,int length) {
        if (s.length() < length) {
            int offSet = length - s.length();
            
            for (int i = 0;i < offSet;i++) {
                s += " ";
            }
        }
        
        return s;
    }
    
    private void print(String s) {
        System.out.println(s);
    }
}
