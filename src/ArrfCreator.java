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
    public static final boolean USE_PORTER_STEMMER = false;
    public static final boolean USE_STOP_WORDS = true;

    //Added features
    public static final boolean COUNT_POS_NEG = true;
    public static final boolean CHECK_POS_NEG = true;
    public static final boolean COUNT_PUNCTUATION = false;
    public static final boolean CHECK_PUNCTUATION = true;
    public static final boolean COUNT_EMOTICONS = false;
    public static final boolean CHECK_EMOTICONS = true;
    public static final boolean COUNT_ELONGATION = false;
    public static final boolean CHECK_ELONGATION = true;

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
        EmoticonCounter efilter = new EmoticonCounter();
        PunctuationCounter punctuationCounter = new PunctuationCounter();
        ElongationCounter elongCounter = new ElongationCounter();

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
                    int count = 0;

                    values[count++] = dataset.attribute(0).addStringValue(cleanString);
                    values[count++] = getCategoryAttributeValue(entryArray[2].replace("\"", ""));

                    if (COUNT_POS_NEG) {
                        values[count++] = swcounter.countPositiveWords(entryArray[3]);
                        values[count++] = swcounter.countNegativeWords(entryArray[3]);
                    }
                    if (CHECK_POS_NEG){
                        values[count++] = getBooleanAttributeValue(swcounter.countPositiveWords(entryArray[3]) > 0);
                        values[count++] = getBooleanAttributeValue(swcounter.countNegativeWords(entryArray[3]) > 0);
                    }
                    if (COUNT_PUNCTUATION) {
                        values[count++] = punctuationCounter.punctuationCount(entryArray[3], '?');
                        values[count++] = punctuationCounter.punctuationCount(entryArray[3], '!');
                    }
                    if (CHECK_PUNCTUATION) {
                        values[count++] = getBooleanAttributeValue(entryArray[3].contains("?"));
                        values[count++] = getBooleanAttributeValue(entryArray[3].contains("!"));
                    }
                    if(COUNT_EMOTICONS){
                        values[count++] = efilter.countPositiveEmoticons(entryArray[3]);
                        values[count++] = efilter.countNegativeEmoticons(entryArray[3]);
                    }
                    if(CHECK_EMOTICONS){
                        values[count++] = getBooleanAttributeValue(efilter.countPositiveEmoticons(entryArray[3]) > 0);
                        values[count++] = getBooleanAttributeValue(efilter.countNegativeEmoticons(entryArray[3]) > 0);
                    }
                    if(COUNT_ELONGATION){
                        values[count++] = elongCounter.countElongation(entryArray[3]);
                    }
                    if(CHECK_ELONGATION){
                        values[count++] = getBooleanAttributeValue(elongCounter.countElongation(entryArray[3]) > 0);
                    }
                    dataset.add(new DenseInstance(1.0, values));
                    values = new double[dataset.numAttributes()];
                }
            }

            StringBuilder fileNameSB = new StringBuilder();
            fileNameSB.append("out/semeval_twitter_data");
            if(!USE_PORTER_STEMMER){
                fileNameSB.append("_unStem");
            }
            if(!USE_STOP_WORDS){
                fileNameSB.append("_unStop");
            }
            if(COUNT_POS_NEG){
                fileNameSB.append("_cntPosNeg");
            }
            if(CHECK_POS_NEG){
                fileNameSB.append("_chkPosNeg");
            }
            if(COUNT_PUNCTUATION){
                fileNameSB.append("_cntPunc");
            }
            if(CHECK_PUNCTUATION){
                fileNameSB.append("_chkPunc");
            }
            if(COUNT_EMOTICONS){
                fileNameSB.append("_cntEmot");
            }
            if(CHECK_EMOTICONS){
                fileNameSB.append("_chkEmot");
            }
            if(COUNT_ELONGATION){
                fileNameSB.append("_countelongation");
            }
            if(CHECK_ELONGATION){
                fileNameSB.append("_checkedelongation");
            }

            fileNameSB.append(".arff");

            System.out.println("File saved as: " + fileNameSB.toString());

            fileOutputStream = new FileOutputStream(fileNameSB.toString());
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
        if(CHECK_POS_NEG){
            List booleanAttrValues = defineBooleanAttrValues();
            result.add(new Attribute("checkPositiveEmoticon", booleanAttrValues));
            result.add(new Attribute("checkNegativeEmoticon", booleanAttrValues));
        }
        if(COUNT_PUNCTUATION){
            List booleanAttrValues = defineBooleanAttrValues();

            result.add(new Attribute("questionMark"));
            result.add(new Attribute("exclamtionMark"));
        }
        if(CHECK_PUNCTUATION){
            List booleanAttrValues = defineBooleanAttrValues();

            result.add(new Attribute("checkquestionMark", booleanAttrValues));
            result.add(new Attribute("checkexclamtionMark", booleanAttrValues));
        }
        if(COUNT_EMOTICONS){
            result.add(new Attribute("positiveEmoticons"));
            result.add(new Attribute("negativeEmoticons"));
        }
        if(CHECK_EMOTICONS){
            List booleanAttrValues = defineBooleanAttrValues();
            result.add(new Attribute("checkPositiveWord", booleanAttrValues));
            result.add(new Attribute("checkNegativeWord", booleanAttrValues));
        }
        if(COUNT_ELONGATION){
            result.add(new Attribute("elongationCount"));
        }
        if(CHECK_ELONGATION){
            List booleanAttrValues = defineBooleanAttrValues();
            result.add(new Attribute("checkElongation", booleanAttrValues));
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
