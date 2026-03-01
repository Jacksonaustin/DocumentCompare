package DocumentCompare;
import java.io.*;
import java.util.*;

import Gui.DocumentComparisonSwing;
import Gui.TermIDF;
import Gui.TfIdf;

public class Main {


    public static class ValuesforWords{

        double rtf; 
        double rtfIdf; 


        public ValuesforWords(double TfIdf, double RTF){
            this.rtf = RTF; 
            this.rtfIdf = TfIdf; 

        }


        public double getRTF(){
            return this.rtf; 
        }

        public double getRtfIdf(){
            return this.rtfIdf;
        }


       
    }



    // make them as clean as possible so you can validate the calculations on paper
    static HashMap<File, HashMap<String, Integer>> outerHashMap = new HashMap<>(); //document // word, count 
    static HashMap<String, Integer> innerHashMap = new HashMap<>(); //word, count
    static HashSet<String> lexicon = new HashSet<>(); // lexicon 
    //each word idf value
    static HashMap<String, Double> idfWordMap = new HashMap<>();


    //store both the rtf and rtf * idf for a given word for each document 
    static HashMap<File, HashMap<String, ValuesforWords>> DocWordValues = new HashMap<>(); //doc to a arraylist which holds the idf and rtf for each word






    public static void main(String[] args) throws Exception{
        File folder = new File("TFIDF");
        File file = new File("TFIDF/01_sports_basketball_game_report.txt"); 

        
        for(File doc : folder.listFiles()){
        

            readFile(doc);

        }
    
        generateValues();
        fixLex();


        HashMap<File, Double> cosinePerDocforDoc = compareDocumentToCorpus(file);


        new TermIDF(idfWordMap, lexicon);
        new TfIdf(DocWordValues, lexicon);
        new DocumentComparisonSwing(cosinePerDocforDoc, file);
    }
    
    public static void readFile(File filename) throws Exception{   // GOOD Don't change i think
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;

         
        String[] ignore ={"of", "from", "and", "the", "if", "of", "from", "by", "or", "am", "is",    
        "is","it","its","itself","just","me","more","most","my","myself",
        "no","nor","not","now","of","off","on","once","only","or","other",
        "our","ours","ourselves","out","over","own","same","she","should",
        "so","some","such","than","that","their","theirs","them","themselves",
        "then","there","these","they","this","those","through","to","too",
        "under","until","up","very","was","we","were","what","when","where",
        "which","while","who","whom","why","will","with","you","your",
        "yours","yourself","yourselves", "as", "an", "at", "\t"
        }; 

        HashMap<String, Integer> perDoc = new HashMap<>(); 

        while((line = br.readLine()) != null){

            line = line.toLowerCase(); // all same lowerscase 
            line = line.replaceAll("[^a-z ]", "");  // remove punctuation
            String[] tokens = line.split("\\s+");  

            for (String i: tokens){
                
                
                if (!Arrays.asList(ignore).contains(i)){

                    //add to lexicon
                    lexicon.add(i);

                    //term doc 
                    if(perDoc.containsKey(i)){
                        perDoc.put(i, perDoc.getOrDefault(i, 0) + 1); 
                    }
                    else{
                        perDoc.put(i, 1); 

                    }


                    //add to total word counts 
                    if(innerHashMap.containsKey(i)){
                        innerHashMap.put(i, innerHashMap.getOrDefault(i, 0) + 1); 
                    }
                    else{
                        innerHashMap.put(i, 1); 

                    }

                   // perDoc.put(i, innerHashMap.getOrDefault(i, 0) + 1);
                }
                
                
            }
            outerHashMap.put(filename, perDoc);

            
        }
        
        br.close();
        
    }

    //COMPRESS LEXICON
    public static void fixLex(){




        //removes words from lexicon in which the document dont have in common 

       


        //remove words only in one doucment, and only appear once 
        


        





    }


    //calulates cosine similarity 
    public static HashMap<File, Double> compareDocumentToCorpus(File DOC){
 

        HashMap<File, Double> docSimilarity = new HashMap<>(); 
        double docAMag = 0; ;

        //document magnitude
     
        for(String word: DocWordValues.get(DOC).keySet()){
            
            if(DocWordValues.get(DOC).get(word) != null){

                docAMag += Math.pow(DocWordValues.get(DOC).get(word).getRtfIdf(), 2.0); 

            }
        }

        docAMag = Math.sqrt(docAMag); 


        for(File doc: DocWordValues.keySet()){

            
            if(doc == DOC){
                continue; 
            }

            double dotProduct = 0; 
            double magnitude = 0; 


            for(String word:DocWordValues.get(doc).keySet()){

                if(DocWordValues.get(DOC).get(word) != null){
                    dotProduct += DocWordValues.get(doc).get(word).rtfIdf * DocWordValues.get(DOC).get(word).rtfIdf; 
                }
             
                if(DocWordValues.get(DOC).get(word) != null){

                    magnitude += Math.pow(DocWordValues.get(doc).get(word).rtfIdf, 2.0);
                }
            }

            magnitude = Math.sqrt(magnitude); 

            docSimilarity.put(doc, dotProduct/ (magnitude * docAMag)); 

        }

        return docSimilarity; 
    }



    public static double RTF(String word, File doc){
        
        double totalTokensofDoc = 0; 

        //get count of word from document hasmap
        double totalCountOfWord = outerHashMap.get(doc).getOrDefault(word,0 ); 

        //retreive the word of document Hashmap
        HashMap<String, Integer> wordMap = outerHashMap.get(doc);

        //calculate total tokens
        for(String i: wordMap.keySet()){

            totalTokensofDoc += wordMap.get(i);
        }

        //don't divide by zero
        if(totalTokensofDoc == 0){
            return 0.0; 
        }

        return totalCountOfWord/totalTokensofDoc; 

    } 

        //caluclate relative term frequency per word and idf

    






    public static void generateValues(){


        HashMap<String, Double> idfMap = new HashMap<>();
        for(String word: lexicon){
            
            Double idf = IDF(word);

            idfMap.put(word, idf );
        }

        idfWordMap = idfMap;


        for(File file: outerHashMap.keySet()){

            HashMap<String, ValuesforWords> wordList = new HashMap<>();
            HashMap<String, Double> rtfInnerMap = new HashMap<>();

            for(String word: outerHashMap.get(file).keySet()){

                double rtf = RTF(word, file);
                rtfInnerMap.put(word, rtf);
                double idf = IDF(word);
                
                double rtfIdf = rtf * idf; 

                if(rtfIdf == 0){
                    rtfIdf++;
                }
                ValuesforWords save = new ValuesforWords(rtfIdf, rtf); 

                wordList.put(word, save);

            }
            DocWordValues.put(file, wordList);
        }


    }


    //called per word in lexicon
    public static double IDF(String word){ // FIX
        
        //System.out.println("cont: "+ containsWord(word));
        
        double inner = outerHashMap.size()/containsWord(word);
        
        //System.out.println(Math.log(inner) / Math.log(2));

        return Math.log(inner) / Math.log(2);
        
    } 

    

    
    
    public static double containsWord(String word){ 
        double truecount = 0;
        for (File file: outerHashMap.keySet()){
            
            if(outerHashMap.get(file).containsKey(word)){
                truecount ++; 
            }

        }
        return truecount; 
    }
    
}
