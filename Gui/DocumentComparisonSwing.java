package Gui;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*; 
import java.awt.BorderLayout;


public class DocumentComparisonSwing extends Object {

    public  static HashMap<File, Double> cos; 
    public static  File cd;



    public DocumentComparisonSwing(HashMap<File, Double> CosineSim, File CD){

        cos = CosineSim; 
        cd = CD; 
        buildGui();

    }
    
    public static void main(String[] args){

    }

    public static void buildGui(){




        //swing intilizination 

        JFrame frame = new JFrame("Document Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);


        

        //intalize table data 
        DefaultTableModel model = new DefaultTableModel();


        model.addColumn("Document");
        model.addColumn("CosineSim");
        for(File doc: cos.keySet()){
            Vector<Object> count = new Vector<>();


            count.add(cos.get(doc)); 
            count.add(0, doc);
            model.addRow(count);
        }


      


        //search for most common doc 

       File best = null;
       Double greatestValue = -1.0; 
        

        for(File file: cos.keySet()){



            if(cos.get(file) > greatestValue && !(cd.getName().equals(file.getName()))){
         
                best = file;
                greatestValue = cos.get(file); 
            }


        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField match = new JTextField("Best math Doc is " + best.toString() + "Similarity: " + greatestValue); 
        frame.setLayout(new BorderLayout());
        
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(match, BorderLayout.SOUTH);
        
        
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.setVisible(true);

    }


    
}
