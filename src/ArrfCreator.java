import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ArrfCreator {

    private static final String FILENAME = "assets/semeval_twitter_data.txt";
    public static final boolean USE_POTTER_STEMMER = false;
    public static final boolean USE_STOP_WORDS = true;
    public static final boolean COUNT_POS_NEG = true;
    public static final boolean CHECK_PUNCTUATION = true;
    public static final boolean COUNT_EMOTICONS = true;

    public static void main(String[] args) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Instances dataset;
        String string = null;
        double[] values;
        FileOutputStream fileOutputStream;

        ArrayList<Attribute> attributes = defineAttributes();

        StringCleaner stringCleaner = new StringCleaner();
        SentimentWordCounter swcounter = new SentimentWordCounter();
        EmoticonFilter efilter = new EmoticonFilter();

        try{
            fileReader = new FileReader(FILENAME);
            bufferedReader = new BufferedReader(fileReader);

            String entry;

            dataset = new Instances("opinion", attributes, 0);
            values = new double[dataset.numAttributes()];

            /*
            entryArray[0] = SID
            entryArray[1] = UID
            entryArray[2] = positive|negative|neutral|objective
            entryArray[3] = TWITTER MESSAGE
             */
            while ((entry = bufferedReader.readLine()) != null){
                String[] entryArray =entry.split("\t");
                String cleanString = stringCleaner.getCleanString(entryArray[3].replace("\"", ""));

                if(!cleanString.isEmpty()) {

                    values[0] = dataset.attribute(0).addStringValue(cleanString);
                    values[1] = getCategoryAttributeValue(entryArray[2].replace("\"", ""));

                    if (COUNT_POS_NEG) {
                        values[2] = swcounter.countPositiveWords(entryArray[3]);
                        values[3] = swcounter.countNegativeWords(entryArray[3]);
                    }

                    if (CHECK_PUNCTUATION) {
                        values[4] = getBooleanAttributeValue(entryArray[3].contains("?"));
                        values[5] = getBooleanAttributeValue(entryArray[3].contains("!"));
                    }

                     if(COUNT_EMOTICONS){
                        values[6] = efilter.countPositiveEmoticons(entryArray[3]);
                        values[7] = efilter.countNegativeEmoticons(entryArray[3]);
                    }
                    dataset.add(new DenseInstance(1.0, values));
                    values = new double[dataset.numAttributes()];
                }
            }

            String filename = "out/semeval_twitter_data";
            if(!USE_POTTER_STEMMER){
                filename += "_unstemmed";
            }
            if(!USE_STOP_WORDS){
                filename += "_unstopped";
            }
            if(COUNT_POS_NEG){
                filename += "_countposneg";
            }
            if(CHECK_PUNCTUATION){
                filename += "_checkedPunctuation";
            }
            if(COUNT_EMOTICONS){
                filename += "_countemoticons";
            }

            fileOutputStream = new FileOutputStream(filename + ".arff");
            fileOutputStream.write(dataset.toString().getBytes());
            fileOutputStream.close();



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

        if(COUNT_POS_NEG){
            result.add(new Attribute("positiveWordCount"));
            result.add(new Attribute("negativeWordCount"));
        }

        if(CHECK_PUNCTUATION){
            List booleanAttrValues = defineBooleanAttrValues();

            result.add(new Attribute("questionMark", booleanAttrValues));
            result.add(new Attribute("exclamtionMark", booleanAttrValues));
        }
        if(COUNT_EMOTICONS){
            result.add(new Attribute("positiveEmoticons"));
            result.add(new Attribute("negativeEmoticons"));
        }
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

    private static List defineBooleanAttrValues() {
        List result = new ArrayList<String>();
        result.add("y");
        result.add("n");
        return result;
    }

    private static double getCategoryAttributeValue(String s) {
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

    private static double getBooleanAttributeValue(boolean check) {
        if (check)
            return 0;
        return 1;
    }

}
