import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class SentimentWordCounter {

    String text;
    ArrayList<String> positiveWordsList;
    ArrayList<String> negativeWordsList;

    public SentimentWordCounter(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try{
            fileReader = new FileReader("assets/Positive.txt");
            bufferedReader = new BufferedReader(fileReader);

            String entry;
            positiveWordsList = new ArrayList<String>();
            while ((entry = bufferedReader.readLine()) != null){
                if(!entry.isEmpty()) {
                    positiveWordsList.add(entry.toLowerCase().replaceAll("[^a-zA-Z]", ""));
                }
            }
            System.out.println(positiveWordsList);

            fileReader = new FileReader("assets/Negative.txt");
            bufferedReader = new BufferedReader(fileReader);

            negativeWordsList = new ArrayList<String>();
            while ((entry = bufferedReader.readLine()) != null){
                if(!entry.isEmpty()) {
                    negativeWordsList.add(entry.toLowerCase().replaceAll("[^a-zA-Z]", ""));
                }
            }
            System.out.println(negativeWordsList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int countPositiveWords(String text){
        int count = 0;
        String[] words = text.split("\\W+");
        for(int i = 0; i < words.length; i++){
            if(positiveWordsList.contains(words[i].toLowerCase())){
                count++;
            }
        }
        return count;
    }

    public int countNegativeWords(String text){
        int count = 0;
        String[] words = text.split("\\W+");
        for(int i = 0; i < words.length; i++){
            if(negativeWordsList.contains(words[i].toLowerCase())){
                count++;
            }
        }
        return count;
    }

}
