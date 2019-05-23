import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class TextData {
    public HashMap<String, Integer> words;
    private String fileName;
    private String textType;

    public String getFileName(){
        return fileName;
    }

    public TextData(String fileName, String textType){
        this.fileName = fileName;
        this.textType = textType;
        words = new HashMap<>();
    }

    public void addWord(String text){
        if(words.containsKey(text))
            words.put(text, words.get(text) + 1);
        else
            words.put(text, 1);
    }

    public String getClassType(){
        return textType;
    }

    public double[] createDocumentInstance(Attributes attributes){
        Iterator<String> wordIter = words.keySet().iterator();
        double [] instance = new double[attributes.size()];
        while(wordIter.hasNext()){
             String current = wordIter.next();
             Integer index = attributes.getAttributeIndex(current);

             if(index!= null){
                 instance[index] = words.get(current);
             }
        }
        return instance;
    }

    public static double[][] createTable(LinkedList<TextData> documents, Attributes attributes){
        double[][] table = new double[documents.size()][attributes.size()];
        Iterator<TextData> docIter = documents.iterator();
        int index = 0;
        while (docIter.hasNext()){
            table[index] = docIter.next().createDocumentInstance(attributes);
            index++;
        }
        return table;

    }





}
