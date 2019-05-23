import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RemoveStopWords {
    ArrayList<String> stopWords;

    public RemoveStopWords() throws IOException {
        stopWords = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("./TurkishStopWords.txt"));

        String word = "";

        while ((word = reader.readLine()) != null){
            stopWords.add(word);
        }
    }

    public boolean isStopWord(String text){
        return stopWords.contains(text);
    }

}
