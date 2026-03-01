package Gui;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DocumentCompare.Main;
import DocumentCompare.Main.ValuesforWords;



public class TfIdf {

    public  static HashMap<File, HashMap<String, ValuesforWords>> docWordCount; 
    public static  HashSet<String> lexicon;



    public TfIdf(HashMap<File, HashMap<String,ValuesforWords>> docWOrkValues, HashSet<String> Lexicon){

        docWordCount = docWOrkValues; 
        lexicon = Lexicon;
        buildGui();

    }
    
    public static void main(String[] args){

    }

    public static void buildGui(){




        //swing intilizination 

        JFrame frame = new JFrame("TF-IDF Matrix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);
        frame.setVisible(true);


        //intalize table data 
        Vector<String> words = new Vector<>();
        words.add("DOCUMENTS");

        if(lexicon != null){
             for(String word: lexicon){

                  words.add(word);

             }
        }

        Vector<Vector<Object>> counts = new Vector<>();


        for(File doc: docWordCount.keySet()){

            Vector<Object> vec = new Vector<>();

            for(String word: lexicon){

                if(docWordCount.get(doc).get(word) != null){
                    vec.add(docWordCount.get(doc).get(word).getRtfIdf());
                }else{
                    vec.add(0.0*0); 
                }
            }
            vec.add(0, doc.getName());
            counts.add(vec);
        }

        



        


        //print table
        DefaultTableModel model = new DefaultTableModel(counts, words);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);

    }

    
}
