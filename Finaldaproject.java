/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaldaproject;

import java.util.*;
import java.io.*;
import org.jgrapht.*;
import org.jgrapht.traverse.*;
import org.jgrapht.graph.*;


/**
 *
 * @author Jeer
 */


public class Finaldaproject {

    /**
     * @param args the command line arguments
     */
    void splitToNchar(String text, ArrayList<String> words,int size){
        int length;
        length = text.length();
        for (int i =0; i < length; i+= size){
            words.add(text.substring(i, Math.min(length, i + size)));
        }
    }
    
    boolean comparechar(String a, String b){
        int count = 0;
        if (a.compareToIgnoreCase(b) == 0){
            return false;
        }
        for (int i =0;i<5;i++){
            if (a.charAt(i) != b.charAt(i)){
                count++;
            }
            if (count > 1){
                return false;
            }
        }
        return true;
        
    }
    
    Finaldaproject(){
        ArrayList<String> words = new ArrayList<String>(); 
        Scanner scan = new Scanner(System.in);

        try{
         Scanner input = new Scanner (new File ("words_5757.txt"));
            while (input.hasNext())
            {
                    String line = input.nextLine().toLowerCase();
                    splitToNchar(line, words, 5);
                
            }
            
        }
        catch (FileNotFoundException e)
        {
            System.err.printf("Error! File not found\r\n");
        }
        DefaultDirectedGraph<String, DefaultEdge> wordladder = new DefaultDirectedGraph<String, DefaultEdge>( DefaultEdge.class);
        for (int i =0 ; i<words.size();i++){
            for (int j =0 ; j<words.size();j++){
                if (comparechar(words.get(i),words.get(j))){
                    Graphs.addEdgeWithVertices(wordladder,words.get(i),words.get(j));
                }
            }
        }
        String start,end;
        System.out.printf("Your starting word: ");
        start = scan.nextLine().toLowerCase();
        System.out.printf("Ending word: ");
        end = scan.nextLine().toLowerCase();

        
    }
    public static void main(String[] args) {
        // TODO code application logic 
        Finaldaproject project = new Finaldaproject();
        
        
    }
    
}