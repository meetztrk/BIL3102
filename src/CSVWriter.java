
import net.sf.javaml.classification.evaluation.PerformanceMeasure;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class CSVWriter {
    public static void exportTable(LinkedList<TextData> documents, Attributes attributes,double[][]table, String fileName) throws IOException {
        new FileWriter("./results/" + fileName + ".csv").close();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./results/" + fileName + ".csv")));

        writer.write(attributes.csvExport());
        writer.newLine();

        Iterator<TextData> docIter = documents.iterator();
        int index =0;
        while (docIter.hasNext()){
            TextData current = docIter.next();
            writer.write(current.getFileName() + ";" + instanceToCSV(table[index]) + current.getClassType());
            writer.newLine();
            index++;
        }
        writer.close();
    }

    private static String instanceToCSV(double[] instance){
        String line = "";
        for(int i = 0; i < instance.length; i++){
            line += instance[i] + ";";
        }
        return line;
    }

    public static void exportPerformance(Map<Object, PerformanceMeasure> pm) throws IOException {
        FileWriter fw = new FileWriter("./results/performanceMetrics.csv");
        fw.write(";");
        int classCount = pm.keySet().size();
        double[][]metrics = new double[3][classCount + 1];
        int index = 0;
        for(Object o:pm.keySet()){
            fw.write(o.toString()+";");
            metrics[0][index] = pm.get(o).getPrecision();
            metrics[0][classCount] += metrics[0][index];

            metrics[1][index] = pm.get(o).getRecall();
            metrics[1][classCount] += metrics[1][index];

            metrics[2][index] = pm.get(o).getFMeasure();
            metrics[2][classCount] += metrics[2][index];
            index++;
        }
        String[] measures = {"Precision", "Recall", "F-Score"};
        fw.write("ortalama\n");
        for(int i = 0; i < 3;i++){
            fw.write(measures[i] + ";");
            for (int j = 0; j < pm.keySet().size(); j++){
                fw.write(metrics[i][j] + ";");
            }
            fw.write((metrics[i][classCount] / classCount)+"\n");
        }
        fw.close();
    }
}
