import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StopWords {
    private static List<String> stopWords = new ArrayList<>();
    private final static String FILENAME = "assets/StopWords.txt";

    public StopWords(){
        readStopWords();
    }

    private void readStopWords() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try{
            fileReader = new FileReader(FILENAME);
            bufferedReader = new BufferedReader(fileReader);

            String entry;

            while ((entry = bufferedReader.readLine()) != null){
                stopWords.add(entry);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isStopWord(String word) {
        if(!ArrfCreator.USE_STOP_WORDS){
            return false;
        }
        if (word.length()<2){
            return true;
        }

        return stopWords.contains(word);
    }
}
