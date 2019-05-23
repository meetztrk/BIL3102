
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Attributes {
    HashMap<String, Integer> hashMap;
    ArrayList<String> arrayList;

    public Attributes(){
        hashMap = new HashMap<>();
        arrayList = new ArrayList<>();
    }

    public void addAttribute(String name){
        if(!hashMap.containsKey(name)){
            int lastIndex = hashMap.size();
            hashMap.put(name, lastIndex);
            arrayList.add(name);
        }
    }

    public Integer getAttributeIndex(String name){
        return hashMap.get(name);
    }

    public String getAttrubiteName(int index){
        return arrayList.get(index);
    }

    public int size(){
        return arrayList.size();
    }

    public String csvExport(){
        String line = "";
        for (String str : arrayList){
            line += str + ";";
        }
        line += "Sinif";
        return line;
    }
    public static Attributes createAttribute(LinkedList<TextData> documents){
        Attributes attributes = new Attributes();

        Iterator<TextData> docIter = documents.iterator();
        while (docIter.hasNext()){
            Iterator<String> words = docIter.next().words.keySet().iterator();
            while (words.hasNext()){
                attributes.addAttribute(words.next());
            }
        }
        return attributes;
    }
}
