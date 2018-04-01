import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringCleaner {

    private static StopWords stopWords = new StopWords();
    private static Stemmer stemmer = new Stemmer();

    public StringCleaner() {
    }


    public String getCleanString(String string) {
        List<String> words = new ArrayList<>();
        String[] wordsArray = string.split(" ");

        for(String word: wordsArray){
            if(!wordIsAUrlOrEmailOrHashtagOrReference(word)) {
                word = word.toLowerCase();
                if (!stopWords.isStopWord(word)) {
                    if (ArrfCreator.USE_POTTER_STEMMER) {
                        word = getStemWord(word);
                    }
                    words.add(word);
                }
            }
        }

        if(words.isEmpty()){
            return "";
        }

        return wordsAsString(words);
    }

    private boolean wordIsAUrlOrEmailOrHashtagOrReference(String word) {
        return wordIsAURL(word)
                || wordIsAnEmail(word)
                || wordIsHashtag(word)
                || wordIsReference(word);
    }

    private boolean wordIsAURL(String word) {
        try {
            new URL(word).toURI();
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return true;
        }
    }

    private boolean wordIsAnEmail(String word) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(word).matches();
    }

    private boolean wordIsHashtag(String word) {
        return word.startsWith("#");
    }

    private boolean wordIsReference(String word) {
        return word.startsWith("@");
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
