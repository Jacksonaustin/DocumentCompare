package Gui;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*; 
import java.awt.BorderLayout;
import java.awt.GridLayout;


public class DocumentComparisonSwing extends Object {

    public  static HashMap<File, Double> cos; 
    public static  File cd;


    public static  class Prior {
        double value; 
        File doc; 


        public Prior(File doc, double value){

            this.value = value;
            this.doc = doc;

        }

    
    }



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

        PriorityQueue<Prior> descOrder = new PriorityQueue<>((a,b) -> Double.compare(a.value, b.value)); 
      
        //add each document and cosine Sim to a priorty queue  
        for(File doc: cos.keySet()){

            descOrder.add(new Prior(doc, cos.get(doc)));
            
        }

        //add to table decesnding order 
        while(!descOrder.isEmpty()){
            Vector<Object> count = new Vector<>();
            Prior p = descOrder.poll();
            count.add(p.value);
            count.add(0, p.doc);
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
        String formatted = String.format("%.4f", greatestValue);
        JTextField match = new JTextField("Best match:  " + best.toString() + " Similarity: " + formatted); 
        frame.setLayout(new BorderLayout());
        JTextField comp = new JTextField("Comparing To: " + cd.toString()); 
        JPanel p = new JPanel(); 

        p.setLayout(new GridLayout(1,2));
        p.add(comp);
        p.add(match);
        
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(p, BorderLayout.SOUTH);
     
        
        
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.setVisible(true);

    }


    
}
