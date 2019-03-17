/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaldaproject;

import java.util.*;
import java.io.*;

/**
 *
 * @author Jeer
 */
public class Finaldaproject {

    /**
     * @param args the command line arguments
     */
    static void splitToNchar(String text, List<String> words,int size){
        int length;
        length = text.length();
        for (int i =0; i < length; i+= size){
            words.add(text.substring(i, Math.min(length, i + size)));
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic 
        List<String> words = new ArrayList<>();
        try{
         Scanner input = new Scanner (new File ("words_5757.txt"));
            while (input.hasNext())
            {
                    String line = input.nextLine();
                    splitToNchar(line, words, 5);
                
            }
            
        }
        catch (FileNotFoundException e)
        {
            System.err.printf("Error! File not found\r\n");
        }
        
        
    }
    
}
