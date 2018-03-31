import java.util.ArrayList;
import java.util.List;

public class StringCleaner {

    private static StopWords stopWords = new StopWords();
    private static Stemmer stemmer = new Stemmer();

    public StringCleaner() {
    }


    public String getCleanString(String string) {
        List<String> words = new ArrayList<>();
        String[] wordsArray = string.split(" ");

        for(String word: wordsArray){
            word = removeNonLetterOrDigits(word);
            word = word.toLowerCase();

            if(!stopWords.isStopWord(word)){
                if(ArrfCreator.USE_POTTER_STEMMER){
                    word = getStemWord(word);
                }
                words.add(word);
            }
        }
        return wordsAsString(words);
    }

    private String removeNonLetterOrDigits(String word) {
        final StringBuilder builder = new StringBuilder();
        for(final char c : word.toCharArray())
            if(Character.isLetterOrDigit(c))
                builder.append(c);
        return builder.toString();
    }

    private String wordsAsString(List<String> words) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String word : words){
            stringBuffer.append(word + " ");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

    private String getStemWord(String word) {
        for(char c: word.toCharArray()){
            stemmer.add(c);
        }
        stemmer.stem();
        return stemmer.toString();
    }
}
