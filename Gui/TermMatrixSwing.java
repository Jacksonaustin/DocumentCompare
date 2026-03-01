package Gui;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class TermMatrixSwing {

    public  static HashMap<File, HashMap<String, Integer>> docWordCount; 
    public static  HashSet<String> lexicon;



    public TermMatrixSwing(HashMap<File, HashMap<String,Integer>> DocWordCount, HashSet<String> Lexicon){

        docWordCount = DocWordCount; 
        lexicon = Lexicon;
        buildGui();

    }
    
    public static void main(String[] args){

    }

    public static void buildGui(){




        //swing intilizination 

        JFrame frame = new JFrame("Term Document Matrix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);


        //intalize table data 
        Vector<String> words = new Vector<>();
        Vector<Vector<Object>> counts = new Vector<>();
        words.add("DOCUMENTS");

        if(lexicon != null){
             for(String word: lexicon){

                  words.add(word);

             }
        }

        for(File doc: docWordCount.keySet()){

            Vector<Object> vec = new Vector<>();

            for(String word: lexicon){

                if(docWordCount.get(doc).get(word) != null){
                    vec.add(docWordCount.get(doc).get(word)); 
                }else{
                    vec.add(0); 
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
