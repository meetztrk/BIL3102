import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.CosineSimilarity;

import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSentenceNormalizer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main{

    public static void main(String[] args) throws Exception {


        Path lookupRoot = Paths.get("./zemberek_data/normalization");
        Path lmFile = Paths.get("./zemberek_data/lm/lm.2gram.slm");

        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
        TurkishSentenceNormalizer normalizer = new TurkishSentenceNormalizer(morphology, lookupRoot, lmFile);

        LinkedList<TextData> documents = new LinkedList<>();
        File parent = new File("./train");
        File[] folders = parent.listFiles();
        for (File folder:folders) {
            documents.addAll(Reader.getAllFilesInDirectory(folder.getPath(),normalizer));
        }
        int trainSize = documents.size();
        parent = new File("./test");
        folders = parent.listFiles();
        for (File folder:folders) {
            documents.addAll(Reader.getAllFilesInDirectory(folder.getPath(),normalizer));
        }

        Attributes attributes = Attributes.createAttribute(documents);
        double[][] table = TextData.createTable(documents,attributes);
        Attributes selectedAttr = FeatureSelection.featureEliminations(attributes,table);
        table = TextData.createTable(documents,selectedAttr);

        Dataset trainData = new DefaultDataset();
        for (int i = 0; i < trainSize; i++){
            Instance instance = new DenseInstance(table[i]);
            instance.setClassValue(documents.get(i).getClassType());
            trainData.add(instance);
        }

        Dataset testData = new DefaultDataset();
        for(int i = 0; i < table.length - trainSize; i++){
            Instance instance = new DenseInstance(table[i + trainSize]);
            instance.setClassValue(documents.get(i + trainSize).getClassType());
            testData.add(instance);
        }

        KNearestNeighbors knn = new KNearestNeighbors(9, new CosineSimilarity());
        knn.buildClassifier(trainData);


        Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(knn, testData);
        CSVWriter.exportPerformance(pm);
    }

}
