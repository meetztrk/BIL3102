import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;


public class FeatureSelection {

    public static Attributes featureEliminations(Attributes post, double[][] table){
        Attributes selectedFeat = new Attributes();

        Dataset dataset = new DefaultDataset();

        for(int i = 0; i < table.length; i++){
            dataset.add(new DenseInstance(table[i]));
        }

        RecursiveFeatureEliminationSVM svm = new RecursiveFeatureEliminationSVM(0.5);
        svm.build(dataset);

        int limit = (int)(svm.noAttributes() * 0.5);

        for (int i = 0; i < svm.noAttributes(); i++){
            if(svm.rank(i)<limit){
                selectedFeat.addAttribute(post.getAttrubiteName(i));
            }
        }

        return selectedFeat;
    }
}
