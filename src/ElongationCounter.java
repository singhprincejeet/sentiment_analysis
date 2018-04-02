import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElongationCounter {

    public ElongationCounter(){
    }

    public int countElongation(String text){
        int count = 0;
        ArrayList<String> elongations = new ArrayList<String>();
        //If a character repeats  or more times, it will be considered an elongation.
        Pattern p = Pattern.compile("(.)\\1{2,}");
        Matcher m = p.matcher(text);
        while(m.find()){
            elongations.add(m.group());
        }
        return elongations.size();
    }

}
