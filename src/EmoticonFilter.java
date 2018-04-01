import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoticonFilter {

    public EmoticonFilter(){
    }

    private ArrayList<String> extractEmoticons(String text){
        ArrayList<String> emoticons = new ArrayList<String>();
        //Regex pattern from Christopher Potts. Modified to include :S
        Pattern p = Pattern.compile("(?:[<>]?[:;=8][\\-o\\*\\']?[\\)\\]\\(\\[dDpPS/\\:\\}\\{@\\|\\\\]|[\\)\\]\\(\\[dDpPS/\\:\\}\\{@\\|\\\\][\\-o\\*\\']?[:;=8][<>]?)");
        Matcher m = p.matcher(text);
        while(m.find()){
            emoticons.add(m.group());
        }
        return emoticons;
    }

    /**
     * Count of positive emoticons. Emoticons have been manually classified as positive/negative
     */
    public int countPositiveEmoticons(String text){
        ArrayList<String> emoticons = extractEmoticons(text);
        int count = 0;
        for(String emoticon : emoticons){
            if(emoticon.matches("^[(\\[Cc].*") || emoticon.matches(".*[\\)\\]DpP9]$")){
                count++;
            }
        }
        return count;
    }

    /**
     * Count of negative emoticons. Emoticons have been manually classified as positive/negative
     */
    public int countNegativeEmoticons(String text){
        ArrayList<String> emoticons = extractEmoticons(text);
        int count = 0;
        for(String emoticon : emoticons){
            if(emoticon.matches("^[)\\]D|@/Ss\\-\\\\].*") || emoticon.matches(".*[(\\[Cc|@/Ss\\-\\\\]$")){
                count++;
            }
        }
        return count;
    }
}
