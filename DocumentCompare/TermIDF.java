package DocumentCompare;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class TermIDF extends Object {

    public  static HashMap<String, Double> idfMap; 
    public static  HashSet<String> lexicon;



    public TermIDF(HashMap<String, Double> idfValues, HashSet<String> Lexicon){

        idfMap = idfValues; 
        lexicon = Lexicon;
        buildGui();

    }
    
    public static void main(String[] args){

    }

    public static void buildGui(){




        //swing intilizination 

        JFrame frame = new JFrame("Term IDF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);


        //intalize table data 
        Vector<Object> words = new Vector<>();
        Vector<Vector<Object>> counts = new Vector<>();


        Vector<Object> count = new Vector<>();

        for(String word: lexicon){
            words.add(word);
            count.add(idfMap.get(word)); 
        }


        counts.add(count);


        

        


        //print table
        DefaultTableModel model = new DefaultTableModel(counts, words);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);

    }


    
}
