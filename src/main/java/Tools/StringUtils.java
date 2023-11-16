package Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     *  当前输入的字符串是否满足正则表达式
     * @param input
     * @param regex
     * @return
     */
    public static boolean isMatching(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 如果传进去的字符串是数字，就返回true，否则返回false
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if(str == null || "".equals(str)){
            return false;
        }
        return str.matches("\\d+");
    }

}
