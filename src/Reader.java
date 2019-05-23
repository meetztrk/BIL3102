
import org.antlr.v4.runtime.Token;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.normalization.TurkishSentenceNormalizer;
import zemberek.tokenization.TurkishTokenizer;
import zemberek.tokenization.antlr.TurkishLexer;

import java.io.*;
import java.util.*;

public class Reader {

    public static LinkedList<TextData> getAllFilesInDirectory(String directoryPath, TurkishSentenceNormalizer normalizer) throws IOException {
        File folder = new File(directoryPath);
        File [] files = folder.listFiles();
        String textType = "" + folder.getName();
        LinkedList<TextData> textList = new LinkedList<>();
        TurkishTokenizer tokenizer = TurkishTokenizer.DEFAULT;
        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();

        RemoveStopWords stopWords = new RemoveStopWords();


        for(int i = 0; i < files.length; i++){
            TextData text = new TextData(files[i].getName(), textType);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(files[i]),"ISO-8859-9"));
            String  buffer = reader.readLine();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer += line;
            }

            List<Token> tokens = tokenizer.tokenize(normalizer.normalize(buffer));
            String stemmed = "";
            Iterator<Token> iter = tokens.iterator();
            while (iter.hasNext()){
                Token temp = iter.next();
                if(temp.getType() != TurkishLexer.Word) continue;
                SentenceAnalysis results = morphology.analyzeAndDisambiguate(temp.getText());
                String word = results.bestAnalysis().get(0).getStem().toLowerCase();

                if(!stopWords.isStopWord(word)){
                    text.addWord(word);
                }

            }

            textList.add(text);
        }
        return textList;
    }


}
