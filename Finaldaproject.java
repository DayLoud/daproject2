/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaldaproject;

import java.util.*;
import java.io.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.*;
import org.jgrapht.alg.util.*;


/**
 *
 * @author Jeer
 */



public class Finaldaproject{

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
    
    int distanceCal(String a, String b){
        int index,distance = 0;
        char first,second;
        for (int i =0;i<5;i++){
            if (a.charAt(i) != b.charAt(i)){
                index = i;
                distance = (int)a.charAt(i)-(int)b.charAt(i);
                break;
            }
            else{
                continue;
            }
        }
        if (distance<0){
            distance*=-1;
        }
        return distance;
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
    
    void wordladder(DefaultDirectedGraph<String, DefaultEdge> wordladder, TreeMap<Character, HashSet<String>> search){
        System.out.println("Word Ladder");
        BFSShortestPath<String, DefaultEdge> path = new BFSShortestPath<>(wordladder);
        String start = null,end = null;
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        while (run){
            try{
                System.out.printf("Your starting word: ");
                start = scan.nextLine().toLowerCase();
                if (search.get(start.charAt(0)).contains(start)){
                    run = false;
                }
                else{
                    System.out.println("Word is not in the dictionary");
                }
            }
            catch(Exception e){
                System.err.println(e);
            }
        }
        run = true;
        while (run){
            try{
                System.out.printf("Ending word: ");
                end = scan.nextLine().toLowerCase();
                if (search.get(end.charAt(0)).contains(end)){
                    run = false;
                }
                else{
                    System.out.println("Word is not in the dictionary");
                }
            }
            catch(Exception e){
                System.err.println(e);
            }
        }

        if (BFSShortestPath.findPathBetween(wordladder, start, end)!=null){
            Iterator<String> iterator = path.findPathBetween(wordladder, start, end).getVertexList().iterator();
            ArrayList<String> shortpath = new ArrayList<>();
            int distance = 0;
            while (iterator.hasNext()) {
                shortpath.add(iterator.next());
            }
            for (int i =0;i<shortpath.size();i++){
                int diff = 0;
                if(i==0){
                    System.out.println(shortpath.get(i));
                }
                else{
                    diff = distanceCal(shortpath.get(i-1),shortpath.get(i));
                    System.out.printf("%s (+%d)\n", shortpath.get(i), diff);
                    distance+=diff;
                }
            }
            System.out.println("Total Cost = " + distance);
        }
        else{
            System.out.println("Cannot Transform!");
        }
    }
    
    void searchdic(TreeMap<Character, HashSet<String>> search){
        System.out.printf("Search Dictionary\nSearch your word: ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        HashSet<String> wordlist = search.get(word.charAt(0));
        Iterator ite = wordlist.iterator();
        boolean match = true,exist = false;
        for (String e:wordlist){
            match = true;
            for (int i = 1;i<word.length();i++){
                if (word.charAt(i) != e.charAt(i)){
                    match= false;
                    break;
                }
            }
            if (match){
                System.out.println(e);
                exist=true;
            }
        }
        if (!exist){
            System.out.println("No matching words");
        }
    }
    
    Finaldaproject(){
        ArrayList<String> words = new ArrayList<>(); 
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
        
        //Setup Map for search
        TreeMap<Character, HashSet<String>> search = new TreeMap<>();
            for (char key = 'a'; key <= 'z'; key++) {
                HashSet<String> set = new HashSet<String>();
                for (int i =0;i<words.size();i++){
                    if (words.get(i).charAt(0) == key){
                        set.add(words.get(i));
                    }
                }
                search.put(key, set);
        }
        
        //Setup Graph for word ladder (Also remove vertex that aren't connected form the dictionary)
        DefaultDirectedGraph<String, DefaultEdge> wordladder = new DefaultDirectedGraph<>( DefaultEdge.class);
        for (int i =0 ; i<words.size();i++){
            for (int j =0 ; j<words.size();j++){
                if (comparechar(words.get(i),words.get(j))){
                    Graphs.addEdgeWithVertices(wordladder,words.get(i),words.get(j));
                }          
            }
            if (!wordladder.containsVertex(words.get(i))){
                wordladder.removeVertex(words.get(i));
                search.get(words.get(i).charAt(0)).remove(words.get(i));
            }
        }
        
        boolean validation = false, run = true;
        String input;
        int choice;
        while(run){
            validation = false;
            
            while (!validation){
                try{
                    System.out.printf("1. Word Ladder\n2. Word Search\n");
                    input = scan.nextLine();
                    choice = Integer.parseInt(input);
                    if (choice == 1){
                        wordladder(wordladder, search);
                        validation = true;
                    }else if (choice == 2){
                        searchdic(search);
                        validation = true;
                    }
                    else{
                        System.err.println("Input 1 or 2");
                    }
                }
                catch(Exception e){
                    System.err.println(e);
                }
            }
            validation = false;
                while (!validation){
                    try{
                        System.out.printf("Continue? (1: Yes || 2: No): ");
                        input = scan.nextLine();
                        choice = Integer.parseInt(input);
                        if(choice == 2){
                            run = false;
                            validation = true;
                        }
                        else if (choice == 1){
                            validation = true;
                        }
                        else {
                            System.err.println("Input 1 or 2");
                        }
                    }
                    catch(Exception e){
                        System.err.println(e);
                    }
                }
            
        }

        
    }
        
    

    public static void main(String[] args) {
        // TODO code application logic 
        Finaldaproject project = new Finaldaproject();
        
        
    }
    
}