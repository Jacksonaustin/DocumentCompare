package DocumentCompare;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class RtfTermDoc {

    public static HashMap<File, HashMap<String,Double>> rtfMap; 
    public static  HashSet<String> lexicon;



    public RtfTermDoc(HashMap<File, HashMap<String,Double>> RtfMap, HashSet<String> Lexicon){

        rtfMap = RtfMap; 
        lexicon = Lexicon;
        buildGui();

    }
    
    public static void main(String[] args){

    }

    public static void buildGui(){




        //swing intilizination 

        JFrame frame = new JFrame("RTF Document Matrix");
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

        for(File doc: rtfMap.keySet()){

            Vector<Object> vec = new Vector<>();

            for(String word: lexicon){

                if(rtfMap.get(doc).get(word) != null){
                    vec.add(rtfMap.get(doc).get(word)); 
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
