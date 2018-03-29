import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class ArrfCreator {

    private static final String FILENAME = "../semeval_twitter_data.txt";

    public static void main(String[] args) {


        ArrayList<Attribute> attributes = defineAttributes();

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Instances dataset;
        String string = null;
        double[] values;

        try{
            fileReader = new FileReader(FILENAME);
            bufferedReader = new BufferedReader(fileReader);

            String entry;

            dataset = new Instances("opinion", attributes, 0);
            values = new double[dataset.numAttributes()];

            while ((entry = bufferedReader.readLine()) != null){
                String[] entryArray =entry.split("\t");
                values[0] = dataset.attribute(0).addStringValue(entryArray[3].replace("\"", ""));
                values[1] = getAttributeValue(entryArray[2].replace("\"", ""));
                dataset.add(new DenseInstance(1.0, values));
                values = new double[dataset.numAttributes()];
            }

            System.out.println(dataset);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static ArrayList<Attribute> defineAttributes() {
        ArrayList<Attribute> result = new ArrayList<>();

        // string type
        result.add(new Attribute("sentence", true));

        //attribute value type
        List categoryAttrValues = defineCategoryAttrValues();
        result.add(new Attribute("category", categoryAttrValues));
        return result;
    }

    private static List defineCategoryAttrValues() {
        List result = new ArrayList<String>();
        result.add("positive");
        result.add("negative");
        result.add("neutral");
        result.add("objective");
        return result;
    }

    private static double getAttributeValue(String s) {
        switch (s){
            case "positive":
                return 0;
            case "negative":
                return 1;
            case "neutral":
                return 2;
            case "objective":
                return 3;
            default:
                return -1;
        }
    }

}
