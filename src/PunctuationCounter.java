public class PunctuationCounter {
    public int punctuationCount(String string, char punctuationymbol){
        int count = 0;
        for (char c: string.toCharArray()){
            if (c == punctuationymbol){
                count++;
            }
        }
        return count;
    }
}
