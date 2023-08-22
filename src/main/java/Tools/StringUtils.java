package Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isMatching(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isNumeric(String str) {
        if(str == null || "".equals(str)){
            return false;
        }
        return str.matches("\\d+");
    }

}
